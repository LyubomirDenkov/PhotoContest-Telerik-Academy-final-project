package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.Notification;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.NotificationRepository;
import application.photocontest.service.contracts.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAll(User user) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        return notificationRepository.getAll(user.getId());
    }


    @Override
    public Notification create(Notification notification) {
        return notificationRepository.create(notification);

    }


}

