package application.photocontest.service.contracts;

import application.photocontest.models.Notification;
import application.photocontest.models.User;

public interface NotificationService {
    Notification getById(User user, int id);

    Notification create(Notification notification);

    void delete(int messageId);
}
