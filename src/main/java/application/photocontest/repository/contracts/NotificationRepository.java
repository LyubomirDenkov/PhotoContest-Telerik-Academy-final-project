package application.photocontest.repository.contracts;

import application.photocontest.models.Notification;

import java.util.List;

public interface NotificationRepository {

    List<Notification> getAll(int id);

    Notification getById(int id);


    Notification create(Notification notification);


    void delete(int id);
}
