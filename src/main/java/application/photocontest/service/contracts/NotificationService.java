package application.photocontest.service.contracts;

import application.photocontest.models.Notification;
import application.photocontest.models.User;

import java.util.List;

public interface NotificationService {
    List<Notification> getAll(User user);

    Notification getById(User user, int id);

    Notification create(Notification notification);

    void delete(int messageId);
}
