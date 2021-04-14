package application.photocontest.services;

import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Category;
import application.photocontest.models.Contest;
import application.photocontest.models.Role;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.service.ContestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static application.photocontest.Helpers.createMockUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContestServiceImplTests {

    @Mock
    ContestRepository contestRepository;

    @InjectMocks
    ContestServiceImpl contestService;


    @Test
    public void getAll_Should_ReturnAllContests_When_IsCalled() {

        List<Contest> result = new ArrayList<>();
        User user = createMockUser();
        user.setRoles(Set.of(new Role(2,"organizer")));


        when(contestService.getAll(user))
                .thenReturn(result);

        contestService.getAll(user);

        verify(contestRepository,times(1)).getAll();
    }

    @Test
    public void getAll_Should_Throw_When_User_IsCalled() {


        User user = createMockUser();


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getAll(user));
    }

}
