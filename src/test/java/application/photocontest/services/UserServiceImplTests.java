package application.photocontest.services;

import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Contest;
import application.photocontest.models.Notification;
import application.photocontest.models.Role;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.NotificationRepository;
import application.photocontest.repository.contracts.PointsRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.UserServiceImpl;
import application.photocontest.service.contracts.ImgurService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static application.photocontest.Helpers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository userRepository;

    @Mock
    ContestRepository contestRepository;

    @Mock
    ImgurService imgurService;

    @Mock
    PointsRepository pointsRepository;

    @Mock
    NotificationRepository notificationRepository;


    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void getAllUsers_Should_Return_All() {
        userService.getAllUsers();
        Mockito.verify(userRepository, Mockito.times(1)).getAllUsers();
    }


    @Test
    public void getAllUsers_Should_Throw_WhenNotAuthorized() {
        User user = createMockUser();

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.getAll(user));

    }

    @Test
    public void getUserContests_Should_Throw_WhenNotAuthorized() {
        User user = createMockUser();


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.getUserContests(user, 5, Optional.empty()));

    }

    @Test
    public void getUserContests_Should_Return_All() {
        Contest contest = createMockContest();
        User user = createMockUser();

        when(contestRepository.getUserContests(user.getId(), Optional.empty())).thenReturn(List.of(contest));

        userService.getUserContests(user, user.getId(), Optional.empty());

        verify(contestRepository, times(1)).getUserContests(user.getId(), Optional.empty());

    }

    @Test
    public void getAll_Should_Return_AllUsers() {
        User organizer = createMockOrganizer();

        when(userService.getAll(organizer)).thenReturn(List.of(organizer));

        userService.getAll(organizer);

        verify(userRepository, times(1)).getAll();

    }

    @Test
    public void getById_Should_Throw_When_Not_Exist() {

        User organizer = createMockOrganizer();

        when(userService.getById(organizer, 254)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userRepository.getById(254));
    }

    @Test
    public void getById_Should_Throw_When_Not_Authorized() {

        User user = createMockUser();
        user.setRoles(new HashSet<>());

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.getById(user, 254));
    }

    @Test
    public void getById_Should_Return_When_IsAuthenticatedAndItsOrganizerOrHisOwnAccount() {

        User user = createMockUser();

        when(userRepository.getById(1)).thenReturn(user);

        userService.getById(user, 1);

        verify(userRepository, times(1)).getById(1);
    }

    @Test
    public void getUserByUserName_Should_Return_User() {

        User user = createMockUser();


        userService.getLeaderboard(user);

        verify(userRepository, times(1)).getLeaderboard();
    }

    @Test
    public void create_Should_Throw_When_DuplicateName() {
        User user = createMockUser();

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.create(user, Optional.empty(), Optional.empty()));
    }


    @Test
    public void create_Should_Create_When_ValidationsOk() throws IOException {
        User user = createMockUser();
        Set<Role> userRoles = new HashSet<>();
        user.setRoles(userRoles);
        Notification notification = createMockNotification();


        when(userRepository.getUserByUserName(user.getUserCredentials().getUserName())).thenThrow(EntityNotFoundException.class);

        when(imgurService.uploadImageToImgurAndReturnUrl(Optional.empty(), Optional.empty())).thenReturn("");

        when(userRepository.create(user)).thenReturn(user);

        when(userRepository.update(user)).thenReturn(user);


        userService.create(user, Optional.empty(), Optional.empty());

        verify(userRepository, times(1)).update(user);
    }


    @Test
    public void getAllPotentialJury_Should_Return_All() {
        userService.getAllPotentialJury();
        Mockito.verify(userRepository, Mockito.times(1)).getAllPotentialJury();
    }


    @Test
    public void update_Should_Throw_WhenNotAuthorized() {
        User user = createMockUser();
        User organizer = createMockOrganizer();


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.update(user, organizer, Optional.empty(), Optional.empty()));

    }

    @Test
    public void update_Should_Update_WhenValidationsOk() throws IOException {
        User user = createMockUser();

        userService.update(user, user, Optional.empty(), Optional.empty());

        verify(userRepository, times(1)).update(user);

    }


    @Test
    public void getUserNotifications_Should_ThrowException_When_UserIsNotAuthenticated() {
        User user = createMockUser();
        user.setRoles(new HashSet<>());


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.getUserNotifications(user, 1));

    }

    @Test
    public void getUserNotifications_Should_ThrowException_When_IsNotUserAccount() {
        User user = createMockUser();
        user.setId(1);


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.getUserNotifications(user, 22));

    }


    @Test
    public void getUserNotifications_Should_ReturnNotifications_When_UserIsAuthenticatedAndItsHisAccount() {
        User user = createMockUser();
        user.setId(1);
        List<Notification> notifications = new ArrayList<>();


        when(notificationRepository.getAll(1)).thenReturn(notifications);

        userService.getUserNotifications(user, 1);

        verify(notificationRepository, times(1)).getAll(1);

    }

}
