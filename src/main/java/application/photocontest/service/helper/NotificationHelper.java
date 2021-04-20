package application.photocontest.service.helper;


import application.photocontest.models.Contest;
import application.photocontest.models.Notification;
import application.photocontest.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static application.photocontest.constants.Constants.*;


public class NotificationHelper {


    public static Notification sendMessageWhenInvitedToJuryOrParticipant(User user, String role, Contest contest) {
        Notification notification = new Notification();
        LocalDateTime timeTillVoting = convertToLocalDateTimeViaSqlTimestamp(contest.getTimeTillVoting());

        notification.setTitle(NOTIFICATION_TITLE);
        notification.setMessage(String.format(CONTEST_INVITATION
                , user.getFirstName(), role, contest.getTitle(),
                timeTillVoting.format(DateTimeFormatter.ofPattern(DATE_FORMAT))));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        notification.setUser(user);

        return notification;
    }

    public static Notification sendMessageWhenSuccessfullyJoinedContest(User user, Contest contest) {
        Notification notification = new Notification();
        LocalDateTime timeTillVoting = convertToLocalDateTimeViaSqlTimestamp(contest.getTimeTillVoting());

        notification.setTitle(SUCCESSFUL_JOINING_TO_CONTEST);
        notification.setMessage(String.format(SUCCESSFUL_JOINING_NOTIFICATION, user.getFirstName(), contest.getTitle(),
                timeTillVoting.format(DateTimeFormatter.ofPattern(DATE_FORMAT))));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        notification.setUser(user);

        return notification;
    }

    public static Notification buildAndCreateNotification(User user, int points, String position, String contestTitle) {
        Notification notification = new Notification();
        notification.setTitle(String.format(MAIL_TITLE_CONTEST_END, contestTitle));
        notification.setMessage(String.format(MESSAGE_CONTEST_END_TOP_POSITION, user.getFirstName(), position, points));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        notification.setUser(user);

        return notification;
    }

    public static Notification sendMessageWhenUserCreated(User user) {
        Notification notification = new Notification();
        notification.setTitle(NOTIFICATION_TITLE_WHEN_REGISTERED);
        notification.setMessage(NOTIFICATION_WHEN_SUCCESSFULLY_REGISTERED);
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        notification.setUser(user);

        return notification;
    }

    public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }

}
