package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.enums.ContestTypes;
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

import static application.photocontest.service.authorization.AuthorizationHelper.verifyIsUserOwnAccount;
import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;
import static application.photocontest.service.constants.Constants.*;

@Service
public class ContestServiceImpl implements ContestService {


    private static final String DEFAULT_CONTEST_BACKGROUND = "https://i.imgur.com/ophF343.jpg";
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final TypeRepository typeRepository;
    private final PointsRepository pointsRepository;
    private final ImageReviewRepository imageReviewRepository;
    private final ImgurService imgurService;
    private final ImageService imageService;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository, UserRepository userRepository,
                              ImageRepository imageRepository, TypeRepository typeRepository,
                              PointsRepository pointsRepository, ImageReviewRepository imageReviewRepository,
                              ImgurService imgurService, ImageService imageService) {
        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.typeRepository = typeRepository;
        this.pointsRepository = pointsRepository;
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
        return contestRepository.getOngoingContests();
    }

    @Override
    public List<Contest> getByUserId(int id) {
        return contestRepository.getByUserId(id);


    }

    @Override
    public void removeImageFromContest(User user, int contestId, int imageId) {
        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        Contest contest = contestRepository.getById(contestId);
        Image image = imageRepository.getById(imageId);

        if (!user.isOrganizer()) {
            validateUserIsJury(contest, user);
        }

        removeImageFromContestCollection(contest,image);

    }

    private void removeImageFromContestCollection(Contest contest, Image image) {

        Set<Image> images = contest.getImages();
        Set<Image> winnerImages = contest.getWinnerImages();

        images.remove(image);
        winnerImages.remove(image);

        contest.setImages(images);
        contest.setWinnerImages(winnerImages);

        contestRepository.update(contest);

    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.getAll();
    }


    @Override
    public List<Contest> getFinishedContests(User user) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        return contestRepository.getFinishedContests();


    }

    @Override
    public List<Contest> getVotingContests(User user) {

        if (user.isOrganizer()) {
            return contestRepository.getVotingContests();
        }

        Optional<Points> points = user.getPoints().stream().findFirst();
        if (points.get().getPoints() < NEEDED_POINTS_TO_BE_JURY) {
            throw new UnauthorizedOperationException(ONLY_JURY_CAN_RATE_IMAGES);
        }

        return contestRepository.getVotingContests();


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
            contestRepository.getContestByImageUploaderId(contest.getId(), user.getId());
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

        if (!contestBackground.isBlank()) {
            contest.setBackgroundImage(contestBackground);
        } else {
            contest.setBackgroundImage(DEFAULT_CONTEST_BACKGROUND);
        }

        Contest contest1 = contestRepository.create(contest);
        addParticipantsToContestAndPoints(contest1, participantSet);
        return contest1;
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
        validateContestPhase(contest, ContestPhases.ONGOING, ADDING_IMAGES_ONLY_IN_PHASE_ONE);
        validateUserIsParticipant(contest, user);
        validateUserNotUploadedImageToContest(contest, user);

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
            pointsRepository.updatePoints(points.get());
            participants.add(participantToAdd);
            userRepository.update(participantToAdd);

        }
        contest.setParticipants(participants);
    }


    @Override
    public void rateImage(User user, int contestId, int imageId, int points, String comment) {

        Contest contest = contestRepository.getById(contestId);
        Image image = imageRepository.getById(imageId);

        boolean isUserRatedImageInContest = true;

        validateContestPhase(contest, ContestPhases.VOTING, PHASE_RATING_ERROR_MESSAGE);
        validateUserIsJury(contest, user);
        validateRatingPointsRange(MIN_RATING_POINTS, MAX_RATING_POINTS, points);

        if (!isContestContainsImage(contest, image)) {
            throw new UnauthorizedOperationException(PHOTO_NOT_IN_A_CONTEST);
        }

        try {
            imageReviewRepository.getImageReviewUserContestAndImageId(user.getId(), contestId, imageId);
        } catch (EntityNotFoundException e) {
            isUserRatedImageInContest = false;
        }

        if (isUserRatedImageInContest) {
            throw new UnauthorizedOperationException(RATING_TWICE_ERROR_MSG);
        }

        ImageReview imageReview = new ImageReview();

        imageReview.setUser(user);
        imageReview.setContest(contest);
        imageReview.setImage(image);
        imageReview.setPoints(points);
        imageReview.setComment(comment);
        imageReviewRepository.create(imageReview);

    }

    @Override
    public Image addImageToContest(User user, int contestId, int imageId) {

        Contest contest = contestRepository.getById(contestId);
        Image image = imageRepository.getById(imageId);

        validateContestPhase(contest, ContestPhases.ONGOING, PHASE_RATING_ERROR_MESSAGE);
        validateUserIsParticipant(contest, user);
        validateUserIsImageUploader(user, image);

        if (isContestContainsImage(contest, image)) {
            throw new UnauthorizedOperationException(PHOTO_ALREADY_IN_A_CONTEST);
        }

        Set<Image> addImage = contest.getImages();
        addImage.add(image);

        contest.setImages(addImage);
        contestRepository.update(contest);
        return image;
    }

    @Override
    public void addUserToContest(User user, int contestId, int userId) {

        verifyUserHasRoles(user, UserRoles.USER);
        verifyIsUserOwnAccount(user.getId(), userId, USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS);

        Contest contest = contestRepository.getById(contestId);
        User userToJoinInContest = userRepository.getById(userId);


        validateContestPhase(contest, ContestPhases.ONGOING, JOIN_OPEN_CONTESTS_ERROR_MESSAGE);
        validateContestType(contest, ContestTypes.OPEN);
        validateUserIsNotParticipantOrJury(contest, userToJoinInContest);


        Set<User> participants = contest.getParticipants();

        if (participants.contains(userToJoinInContest)) {
            throw new DuplicateEntityException(USER_IS_ALREADY_IN_THIS_CONTEST);
        }

        Optional<Points> points = user.getPoints().stream().findFirst();

        int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST;
        points.get().setPoints(pointsToIncrease);
        pointsRepository.updatePoints(points.get());

        userRepository.update(userToJoinInContest);

        participants.add(userToJoinInContest);
        contest.setParticipants(participants);


        contestRepository.update(contest);

    }

    @Override
    public void delete(User user, int id) {


    }


    private void addParticipantsToContestAndPoints(Contest contest, Set<Integer> participantSet) {
        User user;


        Set<User> usersToAdd = new HashSet<>();
        for (Integer participant : participantSet) {
            user = userRepository.getById(participant);

            if (isJuryOrOrganizer(contest.getJury(), user)) {
                continue;
            }

            Optional<Points> points = user.getPoints().stream().findFirst();

            int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
            points.get().setPoints(pointsToIncrease);
            pointsRepository.updatePoints(points.get());
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

    private void validateContestPhase(Contest contest, ContestPhases phase, String message) {
        if (!contest.getPhase().getName().equalsIgnoreCase(phase.toString())) {
            throw new UnauthorizedOperationException(message);
        }
    }

    private void validateRatingPointsRange(int min, int max, int points) {

        if (points < min || points > max) {
            throw new UnauthorizedOperationException(RATING_RANGE_ERROR_MESSAGE);
        }
    }

    private void validateContestType(Contest contest, ContestTypes type) {
        if (!contest.getType().getName().equalsIgnoreCase(type.toString())) {
            throw new UnauthorizedOperationException(JOIN_OPEN_CONTESTS_ERROR_MESSAGE);
        }
    }

    private void validateUserIsParticipant(Contest contest, User user) {
        if (!contest.getParticipants().contains(user)) {
            throw new UnauthorizedOperationException(ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO);
        }
    }

    private void validateUserIsJury(Contest contest, User user) {
        if (!contest.getJury().contains(user)) {
            throw new UnauthorizedOperationException(ONLY_JURY_CAN_RATE_IMAGES);
        }
    }

    private void validateUserIsNotParticipantOrJury(Contest contest, User user) {
        if (contest.getParticipants().contains(user) || contest.getJury().contains(user)) {
            throw new UnauthorizedOperationException(USER_IS_PARTICIPANT_OR_JURY_ERROR_MESSAGE);
        }
    }

    private void validateUserIsImageUploader(User user, Image image) {
        if (image.getUploader().getId() != user.getId()) {
            throw new UnauthorizedOperationException(ADD_ONLY_OWN_PHOTOS);
        }
    }

    private boolean isContestContainsImage(Contest contest, Image image) {
        return contest.getImages().contains(image);
    }

    private void validateUserNotUploadedImageToContest(Contest contest, User user) {
        try {
            contestRepository.getContestByImageUploaderId(contest.getId(), user.getId());
        } catch (EntityNotFoundException e) {
            return;
        }
        throw new UnauthorizedOperationException("Image is already uploaded to contest");
    }

}
