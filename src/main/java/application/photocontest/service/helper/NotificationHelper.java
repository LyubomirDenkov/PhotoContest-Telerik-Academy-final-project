package application.photocontest.service.helper;


import application.photocontest.models.Contest;
import application.photocontest.models.Notification;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.NotificationRepository;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

@Component
public class NotificationHelper {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationHelper(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    public void sendMessageWhenInvitedToJuryOrParticipant(User user, String role, Contest contest) {
        Notification notification = new Notification();
        LocalDateTime timeTillVoting = convertToLocalDateTimeViaSqlTimestamp(contest.getTimeTillVoting());



        notification.setTitle("Invitation");
        notification.setMessage(String.format("Congratulations %s ! You have been invited as a %s in %s contest. " +
                        "Voting phase starts at %s."
                , user.getFirstName(), role, contest.getTitle(),timeTillVoting.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        notification.setUser(user);

        Notification notificationToAdd = notificationRepository.create(notification);
        Set<Notification> userNotifications = user.getNotifications();
        userNotifications.add(notificationToAdd);
        userRepository.update(user);
    }

    public LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }
}
