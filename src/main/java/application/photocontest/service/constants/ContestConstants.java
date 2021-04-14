package application.photocontest.service.constants;

public class ContestConstants {

    //phaseError
    public static final String CONTEST_PHASE_PREPARING = "ongoing";
    public static final String CONTEST_PHASE_VOTING = "voting";
    public static final String CONTEST_PHASE_FINISHED = "finished";
    public static final String CONTEST_PHASE_ERROR_MESSAGE = "You cannot join in a contest which is not in phase one.";

    //ratingError
    public static final String RATING_TWICE_ERROR_MSG = "You cannot rate twice.";
    public static final String PHASE_RATING_ERROR_MESSAGE = "You can only rate images in second phase.";
    public static final String RATING_BETWEEN_1_AND_10 = "You can only give points between 1 and 10.";
    public static final int MAX_RATING = 10;
    public static final String ONLY_JURY_CAN_RATE_IMAGES = "Only jury can rate images.";

    //pointsReward
    public static final int POINTS_REWARD_WHEN_INVITED_TO_CONTEST = 3;
    public static final int POINTS_REWARD_WHEN_JOINING_OPEN_CONTEST = 1;
    public static final int POINTS_WHEN_PARTICIPANT_ADDED = 1;

    //contestError
    public static final String CONTEST_INVITATIONAL_ONLY = "Content is invitational only";
    public static final String USER_IS_ALREADY_IN_THIS_CONTEST = "User is already in this contest.";
    public static final String USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS = "User cannot add other users in contests.";
    public static final String UPDATING_CONTEST_ERROR_MESSAGE = "Only the organizer of the contest can update it.";

    //addingPhotoError
    public static final String PHOTO_ALREADY_IN_A_CONTEST = "This photo is already in the contest.";
    public static final String ADD_ONLY_OWN_PHOTOS = "You can add only own photos.";
    public static final String ONLY_A_PARTICIPANT_CAN_UPLOAD_PHOTO = "Only a participant can upload photo.";
    public static final String ADDING_IMAGES_ONLY_IN_PHASE_ONE = "You can add photos only in phase one.";

    public static final int NEEDED_POINTS_TO_BE_JURY = 150;


    public static final String JOIN_OPEN_CONTESTS_ERROR_MESSAGE = "You can join only in open contests.";
    public static final String USER_IS_PARTICIPANT_OR_JURY_ERROR_MESSAGE = "Only a participant can upload photo.";

    //MAILBOX
    public static final String MAIL_TITLE = "From 'iPhoto' team";

    //UPLOAD IMAGE
    public static final String MAIL_TITLE_UPLOAD_PICTURE = "Successfully upload photo";
    private static final String MESSAGE_UPLOAD_PICTURE = "You successfully upload photo to contest '%s', come back on %s to check your " +
            "result.";

    //JOIN
    public static final String MAIL_TITLE_JOIN_CONTEST = "Successfully join contest '%s'";
    public static final String SUCCESSFULLY_JOIN_CONTEST = "Successfully join in contest '%s', voting phase starts on %s.%n" +
            "You have time to upload your picture before voting date.Enjoy!!";

    //FINISHED
    public static final String MAIL_TITLE_CONTEST_END = "Contest '%s' finished";
    //TOP
    public static final String MESSAGE_CONTEST_END_TOP_POSITION = "Congratulation %s!!%n You are on %s position and awarded with %d points.";

    //MAILBOX ENDS

}
