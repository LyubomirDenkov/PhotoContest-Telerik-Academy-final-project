package application.photocontest.service;


import application.photocontest.models.Notification;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.NotificationRepository;
import application.photocontest.service.contracts.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository notificationRepository;
    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    @Override
    public Notification getById(User user, int id) {
        return notificationRepository.getById(id);
    }
    @Override
    public Notification create(Notification notification) {
        Notification createdNotification = notificationRepository.create(notification);
        return createdNotification;
    }
    @Override
    public void delete(int messageId) {
        notificationRepository.delete(messageId);
    }
}

