package application.photocontest.repository.contracts;

import application.photocontest.models.Notification;

public interface NotificationRepository {
    Notification getById(int id);


    Notification create(Notification notification);


    void delete(int id);
}
