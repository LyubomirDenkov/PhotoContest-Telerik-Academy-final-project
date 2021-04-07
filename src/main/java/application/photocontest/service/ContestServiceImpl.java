package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.ImageRepository;
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

    private static final String CONTEST_PHASE_PREPARING = "ongoing";
    private static final String CONTEST_PHASE_VOTING = "voting";
    private static final String CONTEST_PHASE_FINISHED = "finished";
    private static final String USER_IS_ALREADY_IN_THIS_CONTEST = "User is already in this contest.";
    private static final String USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS = "User cannot add other users in contests.";
    private static final int POINTS_REWARD_WHEN_INVITED_TO_CONTEST = 3;
    private static final int POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST = 1;
    private static final int OPEN = 1;
    public static final int NEEDED_POINTS_TO_BE_JURY = 150;
    public static final String RATING_ERROR_MESSAGE = "Only jury can rate images.";
    public static final String RATING_TWICE_ERROR_MSG = "You cannot rate twice.";
    public static final String PHASE_RATING_ERROR_MESSAGE = "You can only rate images in second phase.";
    public static final String RATING_BETWEEN_1_AND_10 = "You can only give points between 1 and 10.";
    public static final int POINTS_WHEN_PARTICIPANT_ADDED = 1;
    public static final int MAX_RATING = 10;
    public static final String CONTEST_INVITATIONAL_ONLY = "Content is invitational only";
    public static final String CONTEST_PHASE_ERROR_MESSAGE = "You cannot join in a contest which is not in phase one.";
    public static final String PHOTO_ALREADY_IN_A_CONTEST = "This photo is already in the contest.";
    public static final String ADD_ONLY_OWN_PHOTOS = "You can add only own photos.";
    public static final String ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO = "Only a participant can upload photo.";
    public static final String ADDING_IMAGES_ONLY_IN_PHASE_ONE = "You can add photos only in phase one.";
    public static final String UPDATING_CONTEST_ERROR_MESSAGE = "Only the organizer of the contest can update it.";


    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Contest> getAll(User user) {

        List<Contest> contests = contestRepository.getAll();

        //TODO award points if is finished but pointsAward are false


        for (Contest contest : contests) {

            setContestPhase(contest);

            if (contest.getPhase().getName().equals("finished")) {

                if (contest.isPointsAwarded()) {
                    continue;
                }

                //List<User> participants = userRepository.getParticipantsFromContest(contest.getId());

            }

        }


        return contests;
    }


    public List<Contest> getOngoingContests() {

        List<Contest> contests = contestRepository.getOngoingContests();

        if (contests.size() > 6) {
            return contests.subList(0, 6);
        }

        return contests;
    }

    private void setContestPhase(Contest contest) {

        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime contestStartingDate = contest.getStartingDate();
        int phaseOneDays = contest.getPhaseOne();
        int phaseTwoHours = contest.getPhaseTwo();
        if (dateNow.isBefore(contestStartingDate)) {
            contest.setPhase(contestRepository.getPhaseByName(CONTEST_PHASE_PREPARING));
            return;
        }

        if (dateNow.isAfter(contestStartingDate) && dateNow.isBefore(contestStartingDate.plusDays(phaseOneDays))) {
            contest.setPhase(contestRepository.getPhaseByName(CONTEST_PHASE_PREPARING));
            return;
        }


        contestStartingDate = contestStartingDate.plusDays(phaseOneDays);

        if (dateNow.isAfter(contestStartingDate) && dateNow.isBefore(contestStartingDate.plusHours(phaseTwoHours))) {
            contest.setPhase(contestRepository.getPhaseByName(CONTEST_PHASE_VOTING));
            return;
        }

        contest.setPhase(contestRepository.getPhaseByName(CONTEST_PHASE_FINISHED));

    }

    @Override
    public Contest getById(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        return contestRepository.getById(id);
    }

    @Override
    public Contest create(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantSet) {


        boolean ifContestTitleExist = true;

        try {
            contestRepository.getByTitle(contest.getTitle());
        } catch (EntityNotFoundException e) {
            ifContestTitleExist = false;
        }
        if (ifContestTitleExist) {
            throw new DuplicateEntityException("Contest", "title", contest.getTitle());
        }

        setContestPhase(contest);
        setContestJury(jurySet, contest);


        Contest contestToCreate = contestRepository.create(contest);
        addParticipantsToContestAndPoints(contestToCreate, jurySet, participantSet);
        return contestToCreate;

    }

    @Override
    public Contest update(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantsSet) {


        if (!user.getUserCredentials().getUserName().equals(contest.getUser().getUserCredentials().getUserName())) {
            throw new UnauthorizedOperationException(UPDATING_CONTEST_ERROR_MESSAGE);
        }
        setContestJury(jurySet, contest);
        updateParticipants(contest,participantsSet);
        setContestPhase(contest);

        contestRepository.update(contest);

        return contest;

    }

    private void updateParticipants(Contest contest,Set<Integer> participantsSet) {
        Set<User> oldParticipants = contest.getParticipants();

        Set<Integer> dtoParticipants = participantsSet;

        Set<User> jury = contest.getJury();

        Set<User> participants = new HashSet<>();

        Points newPoints = new Points();


        for (Integer participant : dtoParticipants) {
            User participantToAdd = userRepository.getById(participant);
            if (oldParticipants.contains(participantToAdd)) {
                participants.add(participantToAdd);
                continue;
            }
            if (jury.contains(participantToAdd) || participantToAdd.isOrganizer()) continue;

            for (Points point : participantToAdd.getPoints()) {
                newPoints = point;
            }
            int pointsToIncrease = newPoints.getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
            newPoints.setPoints(pointsToIncrease);
            userRepository.updatePoints(newPoints);
            participants.add(participantToAdd);
            userRepository.update(participantToAdd);

        }
        contest.setParticipants(participants);
    }


    @Override
    public void rateImage(User user, int contestId, int imageId, int points, String comment) {

        checkIfUserIsInJury(user, imageId, contestId, points);

        ImageRating imageRating = new ImageRating();

        imageRating.setUser(user);
        imageRating.setImageId(imageId);
        imageRating.setPoints(points);
        imageRating.setComment(comment);
        imageRepository.createJurorRateEntity(imageRating);

        Image image = imageRepository.getById(imageId);
        image.setPoints(image.getPoints() + points);
        imageRepository.update(image);

    }

    @Override
    public Image addImage(User user, int contestId, int imageId) {

        Contest contest = contestRepository.getById(contestId);
        if (!contest.getPhase().getName().equalsIgnoreCase(CONTEST_PHASE_PREPARING)) {
            throw new UnauthorizedOperationException(ADDING_IMAGES_ONLY_IN_PHASE_ONE);
        }
        if (!contest.getParticipants().contains(userRepository.getUserByUserName(user.getUserCredentials().getUserName()))) {
            throw new UnauthorizedOperationException(ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO);
        }

        if (imageRepository.getById(imageId).getUploader().getId() != user.getId()) {
            throw new UnauthorizedOperationException(ADD_ONLY_OWN_PHOTOS);
        }

        if (contest.getImages().contains(imageRepository.getById(imageId))) {
            throw new UnauthorizedOperationException(PHOTO_ALREADY_IN_A_CONTEST);
        }


        Image image = imageRepository.getById(imageId);
        Set<Image> addImage = new HashSet<>();
        addImage.add(image);

        contest.setImages(addImage);
        contestRepository.update(contest);
        return image;
    }


    @Override
    public void addUserToContest(User user, int contestId, int userId) {

        verifyUserHasRoles(user, UserRoles.USER);

        Contest contest = contestRepository.getById(contestId);
        User userToJoinInContest = userRepository.getById(userId);

        if (!userToJoinInContest.getUserCredentials().getUserName().equals(user.getUserCredentials().getUserName())) {
            throw new UnauthorizedOperationException(USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS);
        }

        Phase contestPhase = contestRepository.getPhaseByName(CONTEST_PHASE_PREPARING);
        Type contestType = contestRepository.getTypeById(OPEN);

        if (!contest.getPhase().equals(contestPhase)) {
            throw new UnauthorizedOperationException(CONTEST_PHASE_ERROR_MESSAGE);
        }
        if (contest.getType().equals(contestType)) {
            throw new UnauthorizedOperationException(CONTEST_INVITATIONAL_ONLY);
        }

        Set<User> participants = contest.getParticipants();
        if (participants.contains(userToJoinInContest)) {
            throw new DuplicateEntityException(USER_IS_ALREADY_IN_THIS_CONTEST);
        }


        Points newPoints = new Points();

        for (Points point : userToJoinInContest.getPoints()) {
            newPoints = point;
        }
        int pointsToIncrease = newPoints.getPoints() + POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST;
        newPoints.setPoints(pointsToIncrease);
        userRepository.updatePoints(newPoints);

        userRepository.update(userToJoinInContest);

        participants.add(userToJoinInContest);
        contest.setParticipants(participants);


        contestRepository.update(contest);

    }

    @Override
    public void delete(User user, int id) {
    }

    private Contest checkPointsContestAndImage(int points, int contestId, int imageId) {

        if (points < POINTS_WHEN_PARTICIPANT_ADDED || points > MAX_RATING) {
            throw new IllegalArgumentException(RATING_BETWEEN_1_AND_10);
        }

        Contest contest = contestRepository.getById(contestId);

        if (!contest.getPhase().getName().equalsIgnoreCase(CONTEST_PHASE_VOTING)) {
            throw new UnauthorizedOperationException(PHASE_RATING_ERROR_MESSAGE);
        }

        Image currentImage = imageRepository.getById(imageId);
        if (!contest.getImages().contains(currentImage)) {
            throw new EntityNotFoundException("Image", imageId);
        }
        return contest;
    }

    private void checkIfUserIsInJury(User user, int imageId, int contestId, int points) {

        Contest contest = checkPointsContestAndImage(points, contestId, points);

        boolean isOrganizer = true;
        List<ImageRating> imageRatings = imageRepository.getImageRatingsByUsername(user.getUserCredentials().getUserName());

        for (ImageRating imageRating : imageRatings) {
            if (imageRating.getImageId() == imageId) {
                throw new UnauthorizedOperationException(RATING_TWICE_ERROR_MSG);
            }
        }


        User userToCheck;

    }


    private void addParticipantsToContestAndPoints(Contest contest, Set<Integer> jurySet, Set<Integer> participantSet) {
        User user;

        Points newPoints = new Points();

        Set<User> usersToAdd = new HashSet<>();
        for (Integer participant : participantSet) {
            user = userRepository.getById(participant);
            if (jurySet.contains(participant) || user.isOrganizer()) continue;
            for (Points point : user.getPoints()) {
                newPoints = point;
            }
            int pointsToIncrease = newPoints.getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
            newPoints.setPoints(pointsToIncrease);
            userRepository.updatePoints(newPoints);
            usersToAdd.add(user);
        }
        contest.setParticipants(usersToAdd);
        contestRepository.update(contest);
    }

    private void setContestJury(Set<Integer> jurySet, Contest contest) {
        Set<User> jury = new HashSet<>();

        jury.addAll(userRepository.getOrganizers());

        int userToCheckPoints = 0;

        for (Integer userId : jurySet) {
            User userToAdd = userRepository.getById(userId);
            if (!userToAdd.isUser() || jury.contains(userToAdd)) continue;
            for (Points point : userToAdd.getPoints()) {
                userToCheckPoints = point.getPoints();
            }
            if (userToCheckPoints > NEEDED_POINTS_TO_BE_JURY) {
                jury.add(userToAdd);
            }
        }
        contest.setJury(jury);
    }

}
