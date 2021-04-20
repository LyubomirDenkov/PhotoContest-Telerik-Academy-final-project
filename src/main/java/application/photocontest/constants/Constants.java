package application.photocontest.constants;

public class Constants {

    public static final String FINISHED = "finished";
    public static final String VOTING = "voting";
    public static final String ONGOING = "ongoing";

    public static final String CURRENT_USER = "currentUser";
    //ratingError
    public static final String RATING_TWICE_ERROR_MSG = "You cannot rate twice.";
    public static final String PHASE_RATING_ERROR_MESSAGE = "You can only rate images in second phase.";
    public static final String RATING_RANGE_ERROR_MESSAGE = "You can only give points between 1 and 10.";
    public static final int MAX_RATING_POINTS = 10;
    public static final int MIN_RATING_POINTS = 1;
    public static final String ONLY_JURY_CAN_RATE_IMAGES = "Only jury can rate images.";
    public static final String ONLY_JURY_CAN_REMOVE_IMAGES = "Only jury can remove images.";
    public static final String ONLY_JURY_CAN_ACCESS_VOTING_CONTEST_ERROR_MESSAGE = "Only jury can access voting contests.";
    public static final String USER_WITH_ENOUGTH_POINTS_CAN_ACCESS_VOTING_CONTEST_ERROR_MESSAGE = "Only user with 150 or more points can access voting contests.";


    public static final String DEFAULT_CONTEST_BACKGROUND = "https://i.imgur.com/ophF343.jpg";
    public static final String IMAGE_IS_ALREADY_UPLOADED_ERROR_MESSAGE = "Image is already uploaded to contest";
    public static final String CONTEST_PHASE_IS_NOT_VALID_ERROR_MESSAGE = "Contest phase is not valid";
    public static final String USER_CANNOT_GET_ALL_CONTEST_WITHOUT_PHASE_PARAMETER = "User cannot get all contest without phase parameter";
    public static final String NOT_AUTHORIZED_TO_SEE_OTHER_FINISHED_CONTESTS_WHERE_NOT_PARTICIPATED = "Not authorized to see other finished contests where not participated";

    public static final String INITIAL_PROFILE_IMAGE = "https://i.imgur.com/GdDsxXO.png";
    public static final String SELF_NOTIFICATIONS_ERROR_MESSAGE = "You can view only your notifications.";
    public static final String IS_USER_OWN_ACCOUNT_ERROR_MESSAGE = "Each user can edit his own account.";
    public static final String USER_CAN_ACCESS_ONLY_HIS_OWN_ACCOUNT_ERROR_MESSAGE = "User can access only his own account";
    public static final String USERNAME_ALREADY_EXIST_ERROR_MESSAGE = "Username already exist";
    //pointsReward
    public static final int POINTS_REWARD_WHEN_INVITED_TO_CONTEST = 3;
    public static final int POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST = 1;

    //contestError
    public static final String USER_IS_ALREADY_IN_THIS_CONTEST = "User is already in this contest.";
    public static final String USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS = "User cannot add other users in contests.";
    public static final String UPDATING_CONTEST_ERROR_MESSAGE = "Only the organizer of the contest can update it.";

    //addingPhotoError
    public static final String PHOTO_ALREADY_IN_A_CONTEST = "This photo is already in the contest.";

    public static final String PHOTO_NOT_IN_A_CONTEST = "This photo is not in the contest.";
    public static final String ADD_ONLY_OWN_PHOTOS = "You can add only own photos.";
    public static final String ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO = "Only a participant can upload photo.";
    public static final String ADDING_IMAGES_ONLY_IN_PHASE_ONE = "You can add photos only in phase one.";

    public static final int NEEDED_POINTS_TO_BE_JURY = 150;


    public static final String JOIN_VIEW_OPEN_CONTESTS_ERROR_MESSAGE = "You can join/view only in open contests.";
    public static final String USER_IS_PARTICIPANT_OR_JURY_ERROR_MESSAGE = "Only a participant can upload photo.";

    //FINISHED
    public static final String MAIL_TITLE_CONTEST_END = "Contest '%s' finished";
    //TOP
    public static final String MESSAGE_CONTEST_END_TOP_POSITION = "Congratulation %s!!%n You are on %s position and awarded with %d points.";

    //NOTIFICATION
    public static final String INVITED_AS_JURY = "jury";
    public static final String INVITED_AS_PARTICIPANT = "participant";
    public static final String NOTIFICATION_TITLE = "Invitation";
    public static final String CONTEST_INVITATION = "Congratulations %s ! You have been invited as a %s in %s contest. " +
            "Voting phase starts at %s.";
    public static final String SUCCESFULL_JOINING_TO_CONTEST = "Successful Joining";
    public static final String SUCCESFULL_JOINING_NOTIFICATION = "Congratulations %s ! You have successfully joined %s contest. " +
            "Voting phase starts at %s.";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String NOTIFICATION_TITLE_WHEN_REGISTERED = "Welcome!";
    public static final String NOTIFICATION_WHEN_SUCCESSFULLY_REGISTERED = "Congratulations! You have successfully joined to the iPhoto community!";

}
