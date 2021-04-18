package application.photocontest.service.helper;


import application.photocontest.models.Contest;
import application.photocontest.models.Notification;
import application.photocontest.models.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static application.photocontest.service.constants.Constants.MAIL_TITLE_CONTEST_END;
import static application.photocontest.service.constants.Constants.MESSAGE_CONTEST_END_TOP_POSITION;


public class NotificationHelper {


    public static Notification sendMessageWhenInvitedToJuryOrParticipant(User user,String role, Contest contest) {
        Notification notification = new Notification();
        LocalDateTime timeTillVoting = convertToLocalDateTimeViaSqlTimestamp(contest.getTimeTillVoting());

        notification.setTitle("Invitation");
        notification.setMessage(String.format("Congratulations %s ! You have been invited as a %s in %s contest. " +
                        "Voting phase starts at %s."
                , user.getFirstName(), role, contest.getTitle(), timeTillVoting.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        notification.setUser(user);

        return notification;
    }

    public static Notification sendMessageWhenSuccessfullyJoinedContest(User user, Contest contest) {
        Notification notification = new Notification();
        LocalDateTime timeTillVoting = convertToLocalDateTimeViaSqlTimestamp(contest.getTimeTillVoting());

        notification.setTitle("Successful Joining");
        notification.setMessage(String.format("Congratulations %s ! You have successfully joined %s contest. " +
                "Voting phase starts at %s.", user.getFirstName(), contest.getTitle(), timeTillVoting.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        notification.setUser(user);

       return notification;
    }

    public static Notification buildAndCreateNotification(User user, int points, String position, String contestTitle) {
        Notification notification = new Notification();
        notification.setTitle(String.format(MAIL_TITLE_CONTEST_END, contestTitle));
        notification.setMessage(String.format(MESSAGE_CONTEST_END_TOP_POSITION, user.getFirstName(), position, points));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        notification.setUser(user);

        return notification;
    }

    public static Notification sendMessageWhenUserCreated(User user) {
        Notification notification = new Notification();
        notification.setTitle("Welcome!");
        notification.setMessage("Congratulations! You have successfully joined to the iPhoto community!");
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        notification.setUser(user);


        return notification;
    }

    public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }
}
