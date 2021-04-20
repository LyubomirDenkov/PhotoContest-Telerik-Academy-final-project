package application.photocontest.service.contracts;

import application.photocontest.models.Notification;
import application.photocontest.models.User;

import java.util.List;

public interface NotificationService {
    List<Notification> getAll(User user);

    Notification create(Notification notification);

}
