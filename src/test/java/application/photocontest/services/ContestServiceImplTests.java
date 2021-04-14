package application.photocontest.services;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static application.photocontest.Helpers.*;
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
        User organizer = createMockOrganizer();

        when(contestService.getAll(organizer))
                .thenReturn(result);

        contestService.getAll(organizer);

        verify(contestRepository,times(1)).getAll();
    }

    @Test
    public void getAll_Should_Throw_When_User_IsCalled() {


        User user = createMockUser();


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getAll(user));
    }

    @Test
    public void getById_Should_Throw_When_Not_Exist() {

        User organizer = createMockOrganizer();


        when(contestRepository.getById(254)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> contestService.getById(organizer,254));
    }



    @Test
    public void getById_Should_Return_When_Exist() {

        User organizer = createMockOrganizer();
        Contest contest = createMockContest();


        when(contestRepository.getById(1)).thenReturn(contest);

        contestService.getById(organizer,1);

        Mockito.verify(contestRepository, Mockito.times(1)).getById(1);
    }

}
