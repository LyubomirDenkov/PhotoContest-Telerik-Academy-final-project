package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.enums.ContestTypes;
import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.models.dto.UserActionsDto;
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
import static application.photocontest.constants.Constants.*;
import static application.photocontest.service.helper.NotificationHelper.buildMessageWhenInvitedToJuryOrParticipant;
import static application.photocontest.service.helper.NotificationHelper.buildMessageWhenSuccessfullyJoinedContest;

@Service
public class ContestServiceImpl implements ContestService {


    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final TypeRepository typeRepository;
    private final PointsRepository pointsRepository;
    private final ImageReviewRepository imageReviewRepository;
    private final ImgurService imgurService;
    private final ImageService imageService;
    private final NotificationRepository notificationRepository;


    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository,
                              UserRepository userRepository,
                              ImageRepository imageRepository,
                              TypeRepository typeRepository,
                              PointsRepository pointsRepository,
                              ImageReviewRepository imageReviewRepository,
                              ImgurService imgurService,
                              ImageService imageService,
                              NotificationRepository notificationRepository) {

        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.typeRepository = typeRepository;
        this.pointsRepository = pointsRepository;
        this.imageReviewRepository = imageReviewRepository;
        this.imgurService = imgurService;
        this.imageService = imageService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Contest> getAll(User user, Optional<String> phase) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);
        boolean isOrganizer = false;

        if (user.isOrganizer()) {
            isOrganizer = true;
        }

        if (phase.isEmpty()) {
            if (isOrganizer) {
                return contestRepository.getAll();
            } else {
                throw new UnauthorizedOperationException(USER_CANNOT_GET_ALL_CONTEST_WITHOUT_PHASE_PARAMETER);
            }
        }

        switch (phase.get()) {

            case ONGOING:

                return contestRepository.getOngoingContests(isOrganizer);

            case VOTING:

                if (!user.isOrganizer()) {
                    validateUserHasPointsToSeeVotingContests(user, ONLY_JURY_CAN_ACCESS_VOTING_CONTEST_ERROR_MESSAGE);
                }

                return contestRepository.getVotingContests(user.getId(), isOrganizer);

            case FINISHED:

                return contestRepository.getFinishedContests(user.getId(), isOrganizer);

            default:
                throw new UnsupportedOperationException(CONTEST_PHASE_IS_NOT_VALID_ERROR_MESSAGE);
        }

    }


    @Override
    public List<User> getContestParticipants(User user, int contestId) {
        verifyUserHasRoles(user, UserRoles.ORGANIZER);
        return contestRepository.getContestParticipants(contestId);
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
            validateUserIsJury(contest, user, ONLY_JURY_CAN_REMOVE_IMAGES);
        }

        removeImageAndUpdateContest(contest, image);

    }

    @Override
    public List<Contest> mainPageOngoingContest() {

        List<Contest> contests = contestRepository.mainPageOngoingContests();
        if (contests.size() > 4) {
            return contests.subList(0, 4);
        }

        return contests;
    }


    @Override
    public List<Type> getAllTypes() {
        return typeRepository.getAll();
    }


    private void validateUserHasPointsToSeeVotingContests(User user, String message) {
        Optional<Points> points = user.getPoints().stream().findFirst();
        if (points.isPresent()) {
            if (points.get().getPoints() < NEEDED_POINTS_TO_BE_JURY) {
                throw new UnauthorizedOperationException(message);
            }
        }
    }

    @Override
    public UserActionsDto getUserActionsDto(User user, Contest contest) {

        UserActionsDto userActionsDto = new UserActionsDto();

        if (contest.getJury().contains(user)) {
            userActionsDto.setJury(true);
            return userActionsDto;
        } else {
            userActionsDto.setJury(false);
        }

        if (contest.getParticipants().contains(user)) {
            userActionsDto.setParticipant(true);

        } else {
            userActionsDto.setParticipant(false);
        }
        try {
            contestRepository.getContestByImageUploaderId(contest.getId(), user.getId());
            userActionsDto.setHasImageUploaded(true);
        } catch (EntityNotFoundException e) {
            userActionsDto.setHasImageUploaded(false);
        }

        return userActionsDto;
    }

    @Override
    public Contest getById(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        Contest contest = contestRepository.getById(id);

        if (contest.getPhase().getName().equals(ContestPhases.VOTING.toString())) {
            if (!user.isOrganizer()) {
                validateUserHasPointsToSeeVotingContests(user, USER_WITH_ENOUGH_POINTS_CAN_ACCESS_VOTING_CONTEST_ERROR_MESSAGE);
            }
        }

        return contest;
    }

    @Override
    public Contest create(User user,
                          Contest contest,
                          Set<Integer> jurySet,
                          Set<Integer> participantSet,
                          Optional<MultipartFile> file,
                          Optional<String> url) throws IOException {


        boolean ifContestTitleExist = true;

        try {
            contestRepository.getByTitle(contest.getTitle());
        } catch (EntityNotFoundException e) {
            ifContestTitleExist = false;
        }
        if (ifContestTitleExist) {
            throw new DuplicateEntityException("Contest", "title", contest.getTitle());
        }


        String contestBackground = imgurService.uploadImageToImgur(file, url);

        if (!contestBackground.isBlank()) {
            contest.setBackgroundImage(contestBackground);
        } else {
            contest.setBackgroundImage(DEFAULT_CONTEST_BACKGROUND);
        }

        Contest contestToCreate = contestRepository.create(contest);
        setContestJury(jurySet, contestToCreate);
        addParticipantsToContestAndPoints(contestToCreate, participantSet);
        contestRepository.update(contestToCreate);
        return contestToCreate;
    }

    @Override
    public Contest update(User user,
                          Contest contest,
                          Set<Integer> jurySet,
                          Set<Integer> participantsSet,
                          Optional<MultipartFile> file,
                          Optional<String> url) throws IOException {


        if (!user.getUserCredentials().getUserName().equals(contest.getUser().getUserCredentials().getUserName())) {
            throw new UnauthorizedOperationException(UPDATING_CONTEST_ERROR_MESSAGE);
        }
        setContestJury(jurySet, contest);
        updateParticipants(contest, participantsSet);


        if (file.isPresent() || url.isPresent()) {
            String backgroundImage = imgurService.uploadImageToImgur(file, url);

            if (!backgroundImage.isBlank()) {
                contest.setBackgroundImage(backgroundImage);
            }
        }

        contestRepository.update(contest);

        return contest;

    }

    @Override
    public Image uploadImageToContest(User user,
                                      Image image,
                                      int contestId,
                                      Optional<MultipartFile> file,
                                      Optional<String> url) throws IOException {

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

    @Override
    public List<Image> getContestImages(User user, int contestId) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);
        Contest contest = contestRepository.getById(contestId);

        if (isJuryOrOrganizer(contest.getJury(), user)) {
            return contestRepository.getContestImages(contestId);
        }

        if (contest.getPhase().getName().equals(ContestPhases.FINISHED.toString())) {
            if (contest.getParticipants().contains(user)) {
                return contestRepository.getContestImages(contestId);
            }
        }
        throw new UnauthorizedOperationException(NOT_AUTHORIZED_TO_SEE_OTHER_FINISHED_CONTESTS_WHERE_NOT_PARTICIPATED);
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
            if (points.isPresent()) {
                int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
                points.get().setPoints(pointsToIncrease);
                pointsRepository.update(points.get());
            }
            participants.add(participantToAdd);
            userRepository.update(participantToAdd);

        }
        contest.setParticipants(participants);
    }


    @Override
    public ImageReview rateImage(User user, ImageReview imageReview, int contestId, int imageId) {

        Contest contest = contestRepository.getById(contestId);
        Image image = imageRepository.getById(imageId);

        boolean isUserRatedImageInContest = true;

        validateContestPhase(contest, ContestPhases.VOTING, PHASE_RATING_ERROR_MESSAGE);
        validateUserIsJury(contest, user, ONLY_JURY_CAN_RATE_IMAGES);
        validateRatingPointsRange(MIN_RATING_POINTS, MAX_RATING_POINTS, imageReview.getPoints());

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

        imageReview.setUser(user);
        imageReview.setContest(contest);
        imageReview.setImage(image);

        return imageReviewRepository.create(imageReview);

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
    public void joinContest(User user, int contestId, int userId) {

        verifyUserHasRoles(user, UserRoles.USER);
        verifyIsUserOwnAccount(user.getId(), userId, USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS);

        Contest contest = contestRepository.getById(contestId);
        User userToJoinInContest = userRepository.getById(userId);


        validateContestPhase(contest, ContestPhases.ONGOING, JOIN_VIEW_OPEN_CONTESTS_ERROR_MESSAGE);
        validateContestType(contest, ContestTypes.OPEN);
        validateUserIsNotParticipantOrJury(contest, userToJoinInContest);


        Set<User> participants = contest.getParticipants();

        if (participants.contains(userToJoinInContest)) {
            throw new DuplicateEntityException(USER_IS_ALREADY_IN_THIS_CONTEST);
        }

        Optional<Points> points = userToJoinInContest.getPoints().stream().findFirst();
        if (points.isPresent()) {
            int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST;
            points.get().setPoints(pointsToIncrease);
            pointsRepository.update(points.get());
        }

        addNotificationToUserNotifications(userToJoinInContest, contest);
        userRepository.update(userToJoinInContest);

        participants.add(userToJoinInContest);
        contest.setParticipants(participants);


        contestRepository.update(contest);

    }

    private void addParticipantsToContestAndPoints(Contest contest, Set<Integer> participantSet) {

        User user;
        Set<User> participants = contest.getParticipants();
        for (Integer userId : participantSet) {
            user = userRepository.getById(userId);

            if (isJuryOrOrganizer(contest.getJury(), user)) {
                continue;
            }

            Optional<Points> points = user.getPoints().stream().findFirst();
            if (points.isPresent()) {
                int pointsToIncrease = points.get().getPoints() + POINTS_REWARD_WHEN_INVITED_TO_CONTEST;
                points.get().setPoints(pointsToIncrease);
                pointsRepository.update(points.get());
                addNotificationToUserNotifications(user, contest, INVITED_AS_PARTICIPANT);
                userRepository.update(user);
                participants.add(user);
            }
        }
        contest.setParticipants(participants);
    }

    private void setContestJury(Set<Integer> jurySet, Contest contest) {

        Set<User> jury = new HashSet<>(userRepository.getOrganizers());
        for (User organizer : jury) {
            addNotificationToUserNotifications(organizer, contest, INVITED_AS_JURY);
        }

        for (Integer userId : jurySet) {
            User userToAdd = userRepository.getById(userId);
            if (jury.contains(userToAdd)) continue;

            Optional<Points> points = userToAdd.getPoints().stream().findFirst();

            if (points.isPresent()) {
                if (points.get().getPoints() > NEEDED_POINTS_TO_BE_JURY) {
                    jury.add(userToAdd);
                    addNotificationToUserNotifications(userToAdd, contest, INVITED_AS_JURY);
                    userRepository.update(userToAdd);
                }
            }
        }
        contest.setJury(jury);
    }

    private void addNotificationToUserNotifications(User user, Contest contest, String invitationRole) {
        Notification notification = buildMessageWhenInvitedToJuryOrParticipant(user, invitationRole, contest);
        setNotificationToUser(user, notification);
    }

    private void addNotificationToUserNotifications(User user, Contest contest) {
        Notification notification = buildMessageWhenSuccessfullyJoinedContest(user, contest);
        setNotificationToUser(user, notification);
    }

    private void setNotificationToUser(User user, Notification notification) {

        if (user.getNotifications() == null) {
            user.setNotifications(new HashSet<>());
        }
        Set<Notification> userNotifications = user.getNotifications();
        Notification notificationToAdd = notificationRepository.create(notification);
        userNotifications.add(notificationToAdd);
        user.setNotifications(userNotifications);
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
            throw new UnauthorizedOperationException(JOIN_VIEW_OPEN_CONTESTS_ERROR_MESSAGE);
        }
    }

    private void validateUserIsParticipant(Contest contest, User user) {
        if (!contest.getParticipants().contains(user)) {
            throw new UnauthorizedOperationException(ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO);
        }
    }

    private void validateUserIsJury(Contest contest, User user, String message) {
        if (!contest.getJury().contains(user)) {
            throw new UnauthorizedOperationException(message);
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
        throw new UnauthorizedOperationException(IMAGE_IS_ALREADY_UPLOADED_ERROR_MESSAGE);
    }


    private void removeImageAndUpdateContest(Contest contest, Image image) {

        Set<Image> images = contest.getImages();
        Set<Image> winnerImages = contest.getWinnerImages();

        images.remove(image);
        winnerImages.remove(image);

        contest.setImages(images);
        contest.setWinnerImages(winnerImages);

        contestRepository.update(contest);

    }
}
