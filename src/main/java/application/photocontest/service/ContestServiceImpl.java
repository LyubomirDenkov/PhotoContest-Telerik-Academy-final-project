package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.OrganizerRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class ContestServiceImpl implements ContestService {

    public static final String USER_IS_ALREADY_IN_THIS_CONTEST = "User is already in this contest.";
    public static final String USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS = "User cannot add other users in contests.";
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository, UserRepository userRepository, OrganizerRepository organizerRepository, ImageRepository imageRepository) {
        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Contest> getAll(UserCredentials userCredentials) {

        List<Contest> contests = contestRepository.getAll();

        //TODO award points if is finished but pointsAward are false

        for (int i = 0; i < contests.size(); i++) {

            if (contests.get(i).getPhase().getName().equals("finished")) {
                continue;
            }

            LocalDateTime dateNow = LocalDateTime.now();
            LocalDateTime contestStartingDate = contests.get(i).getStartingDate();
            int phaseOneDays = contests.get(i).getPhaseOne();
            int phaseTwoHours = contests.get(i).getPhaseTwo();

            if (dateNow.isBefore(contestStartingDate)) {
                continue;
            }

            if (dateNow.isAfter(contestStartingDate) &&
                    dateNow.plusDays(phaseOneDays).isBefore(contestStartingDate.plusDays(phaseOneDays).plusHours(phaseTwoHours))) {
                contests.get(i).setPhase(contestRepository.getByPhase(2));
                continue;
            }

            dateNow = dateNow.plusDays(phaseOneDays).plusHours(phaseTwoHours);
            contestStartingDate = contestStartingDate.plusDays(phaseOneDays).plusHours(phaseTwoHours);

            if (dateNow.isAfter(contestStartingDate)) {
                contests.get(i).setPhase(contestRepository.getByPhase(3));
            }

        }


        return contests;
    }

    @Override
    public Contest getById(UserCredentials organizer, int id) {
        return contestRepository.getById(id);
    }

    @Override
    public Contest create(Organizer organizer, Contest contest) {

        boolean ifContestTitleExist = true;

        try {
            contestRepository.getByTitle(contest.getTitle());
        } catch (EntityNotFoundException e) {
            ifContestTitleExist = false;
        }
        if (ifContestTitleExist) {
            throw new DuplicateEntityException("Contest", "title", contest.getTitle());
        }

        Contest contestToCreate = contestRepository.create(contest);
        addParticipantsToContestAndPoints(contestToCreate);
        return contestToCreate;

    }

    @Override
    public Contest update(Organizer organizer, Contest contest, ContestDto contestDto) {


        if (!organizer.getCredentials().getUserName().equals(contest.getOrganizer().getCredentials().getUserName())) {
            throw new UnauthorizedOperationException("something");
        }

        Set<User> participants = contest.getParticipants();


        Set<User> newParticipants = new HashSet<>();

        for (Integer participant : contestDto.getParticipants()) {
            User participantToAdd = userRepository.getById(participant);
            if (participants.contains(participantToAdd)) continue;

            participantToAdd.setPoints(participantToAdd.getPoints() + 3);
            newParticipants.add(participantToAdd);
            participants.addAll(newParticipants);
            userRepository.update(participantToAdd);

        }


        contest.setParticipants(newParticipants);
        contestRepository.update(contest);

        return contest;

    }


    @Override
    public void delete(Organizer organizer, int id) {
    }

    @Override
    public void addUserToContest(UserCredentials userCredentials, int contestId, int userId) {
        verifyUserHasRoles(userCredentials, UserRoles.USER);

        Contest contest = contestRepository.getById(contestId);
        User user = userRepository.getById(userId);

        if (!user.getCredentials().getUserName().equals(userCredentials.getUserName())) {
            throw new UnauthorizedOperationException(USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS);
        }
        if (contest.getType().getName().equalsIgnoreCase("open")) {
            if (!contest.getPhase().getName().equalsIgnoreCase("phase |")) {
                throw new UnauthorizedOperationException("You cannot join in a contest which is not in phase |.");
            }
        } else {
            throw new UnauthorizedOperationException("You cannot join in an invitational contest.");
        }

        Set<User> participants = contest.getParticipants();
        if (participants.contains(user)) {
            throw new DuplicateEntityException(USER_IS_ALREADY_IN_THIS_CONTEST);
        }


        participants.add(user);
        contest.setParticipants(participants);
        user.setPoints(user.getPoints() + 1);
        userRepository.update(user);
        contestRepository.update(contest);

    }

    @Override
    public void rateImage(UserCredentials userCredentials, int contestId, int imageId, int points, String comment) {

        checkIfUserIsInJury(userCredentials,imageId,contestId,points);

        ImageRating imageRating = new ImageRating();

        imageRating.setUserCredentials(userCredentials);
        imageRating.setImageId(imageId);
        imageRating.setPoints(points);
        imageRating.setComment(comment);
        imageRepository.createJurorRateEntity(imageRating);


    }

    private Contest checkPointsContestAndImage(int points, int contestId, int imageId) {

        if (points < 1 || points > 10) {
            throw new IllegalArgumentException("You can only give points between 1 and 10.");
        }

        Contest contest = contestRepository.getById(contestId);

        if (!contest.getPhase().getName().equalsIgnoreCase("phaseTwo")) {
            throw new UnauthorizedOperationException("You can only rate images in second phase.");
        }

        Image currentImage = imageRepository.getById(imageId);
        if (!contest.getImages().contains(currentImage)) {
            throw new EntityNotFoundException("Image", imageId);
        }
        return contest;
    }

    private void checkIfUserIsInJury(UserCredentials userCredentials, int imageId, int contestId,int points) {

        Contest contest = checkPointsContestAndImage(points,contestId,points);

        boolean isOrganizer = true;
        List<ImageRating> imageRatings = imageRepository.getImageRatingsByUsername(userCredentials.getUserName());

        for (ImageRating imageRating : imageRatings) {
            if (imageRating.getImageId() == imageId) {
                throw new UnauthorizedOperationException("You cannot rate twice.");
            }
        }


        Organizer organizer;
        try {
            organizer = organizerRepository.getByUserName(userCredentials.getUserName());

        } catch (EntityNotFoundException e) {
            isOrganizer = false;
        }



        User user;

        if (!isOrganizer) {
            user = userRepository.getUserByUserName(userCredentials.getUserName());

            if (!contest.getJury().contains(user)) {
                throw new UnauthorizedOperationException("Only jury can rate images.");

            }
        }
    }


    private void addParticipantsToContestAndPoints(Contest contest) {
        Set<User> participants = contest.getParticipants();
        for (User participant : participants) {
            participant.setPoints(participant.getPoints() + 3);
            userRepository.update(participant);
        }
        contest.setParticipants(participants);
        contestRepository.update(contest);
    }


}
