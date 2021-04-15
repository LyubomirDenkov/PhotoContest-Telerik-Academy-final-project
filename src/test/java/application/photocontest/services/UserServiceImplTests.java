package application.photocontest.services;

import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static application.photocontest.Helpers.createMockContest;
import static application.photocontest.Helpers.createMockUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository userRepository;

    @Mock
    ContestRepository contestRepository;


    @InjectMocks
    UserServiceImpl userService;





    @Test
    public void getAllUsers_Should_Return_All() {
        userService.getAllUsers();
        Mockito.verify(userRepository,Mockito.times(1)).getAllUsers();
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
                () -> userService.getUserContests(user,5));

    }

    @Test
    public void getUserContests_Should_Return_All() {
        Contest contest = createMockContest();
        User user = createMockUser();

        when(contestRepository.getUserContests(user.getId())).thenReturn(List.of(contest));

        userService.getUserContests(user,user.getId());

        verify(contestRepository,times(1)).getUserContests(user.getId());


    }


}
