package application.photocontest.services;


import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Notification;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.NotificationRepository;
import application.photocontest.service.NotificationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static application.photocontest.Helpers.createMockNotification;
import static application.photocontest.Helpers.createMockUser;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTests {

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;



    @Test
    public void getAll_Should_Get_When_IsCalled() {
        Notification notification = createMockNotification();
        User user = createMockUser();

        Mockito.when(notificationRepository.getById(notification.getId())).thenReturn(notification);

        notificationService.getById(user,notification.getId());

        Mockito.verify(notificationRepository,Mockito.times(1)).getById(notification.getId());

    }

    @Test
    public void create_Should_create_When_IsCalled() {
        Notification notification = createMockNotification();

        notificationService.create(notification);

        Mockito.verify(notificationRepository,Mockito.times(1)).create(notification);

    }
    @Test
    public void delete_Should_Delete_When_IsCalled() {
        Notification notification = createMockNotification();

        notificationService.delete(notification.getId());

        Mockito.verify(notificationRepository,Mockito.times(1)).delete(notification.getId());

    }



}
