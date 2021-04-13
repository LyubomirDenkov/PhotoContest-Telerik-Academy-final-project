package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.*;
import application.photocontest.service.contracts.ContestService;
import application.photocontest.service.contracts.ImageService;
import application.photocontest.service.contracts.ImgurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;
import static application.photocontest.service.constants.ContestConstants.*;

@Service
public class ContestServiceImpl implements ContestService {


    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final TypeRepository typeRepository;
    private final PhaseRepository phaseRepository;
    private final ImageReviewRepository imageReviewRepository;
    private final ImgurService imgurService;

    private final ImageService imageService;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository, UserRepository userRepository, ImageRepository imageRepository, TypeRepository typeRepository, PhaseRepository phaseRepository, ImageReviewRepository imageReviewRepository, ImgurService imgurService, ImageService imageService) {
        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.typeRepository = typeRepository;
        this.phaseRepository = phaseRepository;
        this.imageReviewRepository = imageReviewRepository;
        this.imgurService = imgurService;
        this.imageService = imageService;
    }

    @Override
    public List<Contest> getAll(User user) {
        verifyUserHasRoles(user, UserRoles.ORGANIZER);
        return contestRepository.getAll();
    }


    public List<Contest> getOngoingContests() {

        List<Contest> contests = contestRepository.getOngoingContests();

        if (contests.size() > 6) {
            return contests.subList(0, 6);
        }

        return contests;
    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.getAll();
    }

    @Override
    public Set<Image> getContestImages(int id) {
        Contest contest = contestRepository.getById(id);
        Set<Image> images = contest.getImages();

        return images;
    }

    @Override
    public List<Contest> getFinishedContests() {

        List<Contest> contests = contestRepository.getFinishedContests();

        if (contests.size() > 6) {
            return contests.subList(0, 6);
        }

        return contests;
    }

    @Override
    public List<Contest> getVotingContests() {
        List<Contest> contests = contestRepository.getFinishedContests();

        if (contests.size() > 6) {
            return contests.subList(0, 6);
        }

        return contests;
    }

    @Override
    public Contest getById(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        Contest contest = contestRepository.getById(id);


        if (contest.getJury().contains(user)) {
            contest.setIsJury(true);
            return contest;
        }

        if (contest.getParticipants().contains(user)) {
            contest.setParticipant(true);
        } else {
            return contest;
        }

        try {
            contestRepository.getContestByImageUploaderId(user.getId());
            contest.setHasImageUploaded(true);
        } catch (EntityNotFoundException e) {
            contest.setHasImageUploaded(false);
        }

        return contest;
    }

    @Override
    public Contest create(User user, Contest contest, Set<Integer> jurySet,
                          Set<Integer> participantSet, Optional<MultipartFile> file, Optional<String> url) throws IOException {


        boolean ifContestTitleExist = true;

        try {
            contestRepository.getByTitle(contest.getTitle());
        } catch (EntityNotFoundException e) {
            ifContestTitleExist = false;
        }
        if (ifContestTitleExist) {
            throw new DuplicateEntityException("Contest", "title", contest.getTitle());
        }


        setContestJury(jurySet, contest);

        String contestBackground = imgurService.uploadImageToImgurAndReturnUrl(file, url);
        contest.setBackgroundImage(contestBackground);


        Contest contestToCreate = contestRepository.create(contest);
        addParticipantsToContestAndPoints(contestToCreate, jurySet, participantSet);

        return contestToCreate;

    }

    @Override
    public Contest update(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantsSet,
                          Optional<MultipartFile> file, Optional<String> url) throws IOException {


        if (!user.getUserCredentials().getUserName().equals(contest.getUser().getUserCredentials().getUserName())) {
            throw new UnauthorizedOperationException(UPDATING_CONTEST_ERROR_MESSAGE);
        }
        setContestJury(jurySet, contest);
        updateParticipants(contest, participantsSet);


        if (file.isPresent() || url.isPresent()) {
            String backgroundImage = imgurService.uploadImageToImgurAndReturnUrl(file, url);
            contest.setBackgroundImage(backgroundImage);
        }

        contestRepository.update(contest);

        return contest;

    }


    //TODO
    @Override
    public Image uploadImageToContest(User user, Image image, int contestId, Optional<MultipartFile> file, Optional<String> url) throws IOException {

        Contest contest = contestRepository.getById(contestId);

        validateUserIsImageUploader(user, image);
        validateContestPhase(contest, ContestPhases.ONGOING.toString());
        validateUserIsParticipant(contest, user);
        validateUserNotUploadedImageToContest(user);

        Image imageAddToContest = imageService.create(user, image, file, url);


        Set<Image> contestImages = contest.getImages();
        contestImages.add(imageAddToContest);

        contest.setImages(contestImages);
        contestRepository.update(contest);
        return image;

    }

    private void updateParticipants(Contest contest, Set<Integer> newParticipants) {

        Set<User> oldParticipants = contest.getParticipants();


        Set<User> jury = contest.getJury();

        Set<User> participants = new HashSet<>();


        for (Integer participant : newParticipants) {

            User participantToAdd = userRepository.getById(participant);
            if (oldParticipants.contains(participantToAdd)) {
                participants.add(participantToAdd);
                continue;
            }
            if (jury.contains(participantToAdd) || participantToAdd.isOrganizer()) continue;

            Optional<Points> points = participantToAdd.getPoints().stream().findFirst();

            int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
            points.get().setPoints(pointsToIncrease);
            userRepository.updatePoints(points.get());
            participants.add(participantToAdd);
            userRepository.update(participantToAdd);

        }
        contest.setParticipants(participants);
    }


    @Override
    public void rateImage(User user, int contestId, int imageId, int points, String comment) {

        checkIfUserIsInJury(user, imageId, contestId, points);

        ImageReview imageReview = new ImageReview();
        Image image = imageRepository.getById(imageId);
        Contest contest = contestRepository.getById(contestId);

        imageReview.setUser(user);
        imageReview.setContest(contest);
        imageReview.setImage(image);
        imageReview.setPoints(points);
        imageReview.setComment(comment);
        imageRepository.createJurorRateEntity(imageReview);

        image.setPoints(image.getPoints() + points);

        imageRepository.update(image);

    }

    @Override
    public Image addImageToContest(User user, int contestId, int imageId) {

        Contest contest = contestRepository.getById(contestId);
        Image image = imageRepository.getById(imageId);

        validateContestPhase(contest, ContestPhases.ONGOING.toString());
        validateUserIsParticipant(contest, user);
        validateUserIsImageUploader(user, image);
        validateContestNotContainsImage(contest, image);

        Set<Image> addImage = contest.getImages();
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

        checkBeforeAddUser(userToJoinInContest, user, contest);

        Set<User> participants = contest.getParticipants();

        if (participants.contains(userToJoinInContest)) {
            throw new DuplicateEntityException(USER_IS_ALREADY_IN_THIS_CONTEST);
        }

        Optional<Points> points = user.getPoints().stream().findFirst();

        int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST;
        points.get().setPoints(pointsToIncrease);
        userRepository.updatePoints(points.get());

        userRepository.update(userToJoinInContest);

        participants.add(userToJoinInContest);
        contest.setParticipants(participants);


        contestRepository.update(contest);

    }

    @Override
    public void delete(User user, int id) {


    }

    private Contest checkPointsContestAndImage(int contestId, int imageId, int points) {

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

        Contest contest = checkPointsContestAndImage(contestId, imageId, points);

        List<ImageReview> imageReviews = imageReviewRepository
                .getImageRatingsByUsername(user.getUserCredentials().getUserName());

        for (ImageReview imageReview : imageReviews) {
            if (imageReview.getImage().getId() == imageId) {
                throw new UnauthorizedOperationException(RATING_TWICE_ERROR_MSG);
            }
        }

        if (!contest.getJury().contains(user)) {
            throw new UnauthorizedOperationException(ONLY_JURY_CAN_RATE_IMAGES);
        }
    }


    private void addParticipantsToContestAndPoints(Contest contest, Set<Integer> jurySet, Set<Integer> participantSet) {
        User user;


        Set<User> usersToAdd = new HashSet<>();
        for (Integer participant : participantSet) {
            user = userRepository.getById(participant);

           /* if (isJuryOrOrganizer(jurySet, user)) {
                continue;
            }*/

            Optional<Points> points = user.getPoints().stream().findFirst();

            int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
            points.get().setPoints(pointsToIncrease);
            userRepository.updatePoints(points.get());
            usersToAdd.add(user);
        }
        contest.setParticipants(usersToAdd);
        contestRepository.update(contest);
    }

    private void setContestJury(Set<Integer> jurySet, Contest contest) {

        Set<User> jury = new HashSet<>();

        jury.addAll(userRepository.getOrganizers());

        for (Integer userId : jurySet) {
            User userToAdd = userRepository.getById(userId);
            if (jury.contains(userToAdd)) continue;

            Optional<Points> points = userToAdd.getPoints().stream().findFirst();

            if (points.get().getPoints() > NEEDED_POINTS_TO_BE_JURY) {
                jury.add(userToAdd);
            }
        }
        contest.setJury(jury);
    }

    private boolean isJuryOrOrganizer(Set<User> jury, User user) {
        return jury.contains(user) || user.isOrganizer();
    }

    private void validateContestPhase(Contest contest, String phase) {
        if (!contest.getPhase().getName().equalsIgnoreCase(phase)) {
            throw new UnauthorizedOperationException(ADDING_IMAGES_ONLY_IN_PHASE_ONE);
        }
    }

    private void validateUserIsParticipant(Contest contest, User user) {
        if (!contest.getParticipants().contains(user)) {
            throw new UnauthorizedOperationException(ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO);
        }
    }

    private void validateUserIsImageUploader(User user, Image image) {
        if (image.getUploader().getId() != user.getId()) {
            throw new UnauthorizedOperationException(ADD_ONLY_OWN_PHOTOS);
        }
    }

    private void validateContestNotContainsImage(Contest contest, Image image) {
        if (contest.getImages().contains(image)) {
            throw new UnauthorizedOperationException(PHOTO_ALREADY_IN_A_CONTEST);
        }
    }

    private void validateUserNotUploadedImageToContest(User user) {
        try {
            contestRepository.getContestByImageUploaderId(user.getId());
        } catch (EntityNotFoundException e) {
            return;
        }
        throw new UnauthorizedOperationException("Image is already uploaded to contest");
    }


    private void checkBeforeAddUser(User userToJoinInContest, User user, Contest contest) {

        if (!userToJoinInContest.equals(user)) {
            throw new UnauthorizedOperationException(USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS);
        }
        if (!contest.getPhase().equals(phaseRepository.getPhaseByName(CONTEST_PHASE_PREPARING))) {
            throw new UnauthorizedOperationException(CONTEST_PHASE_ERROR_MESSAGE);
        }
        if (!contest.getType().equals(typeRepository.getById(1))) {
            throw new UnauthorizedOperationException(CONTEST_INVITATIONAL_ONLY);
        }

    }

}
