package application.photocontest.services;

import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.TypeRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.ContestServiceImpl;
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
public class ContestServiceImplTests {

    @Mock
    ContestRepository contestRepository;

    @Mock
    TypeRepository typeRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ImgurService imgurService;

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

    @Test
    public void getOngoing_Should_Return_When_IsCalled() {
        List<Contest> result = new ArrayList<>();

        when(contestRepository.getOngoingContests()).thenReturn(result);

        contestService.getOngoingContests();

        Mockito.verify(contestRepository, Mockito.times(1)).getOngoingContests();
    }

    @Test
    public void getFinished_Should_Return_When_IsCalled() {
        List<Contest> result = new ArrayList<>();
        User user = createMockUser();

        when(contestRepository.getFinishedContests()).thenReturn(result);

        contestService.getFinishedContests(user);

        Mockito.verify(contestRepository, Mockito.times(1)).getFinishedContests();
    }

    @Test
    public void getVoting_Should_Return_When_OrganizerCalls() {
        List<Contest> result = new ArrayList<>();
        User organizer = createMockOrganizer();

        when(contestRepository.getVotingContests()).thenReturn(result);

        contestService.getVotingContests(organizer);

        Mockito.verify(contestRepository, Mockito.times(1)).getVotingContests();
    }

    @Test
    public void getVoting_Should_Return_When_UserCalls() {
        List<Contest> result = new ArrayList<>();
        User user = createMockUser();


        when(contestRepository.getVotingContests()).thenReturn(result);

        contestService.getVotingContests(user);

        Mockito.verify(contestRepository, Mockito.times(1)).getVotingContests();
    }

    @Test
    public void getVoting_Should_Throw_When_UserWithLessPoints() {
        User user = createMockUser();
        user.setPoints(Set.of(new Points(2,50)));


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getVotingContests(user));
    }

    @Test
    public void getByUserId_Should_Return_When_IsCalled() {
        List<Contest> result = new ArrayList<>();
        User user = createMockUser();


        when(contestRepository.getByUserId(user.getId())).thenReturn(result);

        contestService.getByUserId(user.getId());

        Mockito.verify(contestRepository, Mockito.times(1)).getByUserId(user.getId());
    }

    @Test
    public void getAllTypes_Should_Return_When_IsCalled() {
        List<Type> result = new ArrayList<>();

        when(typeRepository.getAll()).thenReturn(result);

        contestService.getAllTypes();

        Mockito.verify(typeRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void create_Should_Throw_When_DuplicateName() {
        Contest contest = createMockContest();
        Set<Integer> jury = Set.of(1,2,3);
        Set<Integer> participants = Set.of(4,5,6);

        Mockito.when(contestRepository.getByTitle(contest.getTitle())).thenReturn(contest);

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> contestService.create(contest.getUser(),contest,jury,participants, Optional.empty(),Optional.empty()));
    }

    @Test
    public void create_Should_Create_When_ValidationsOk() throws IOException {
        Contest contest = createMockContest();
        User user = createMockUser();
        User organizer = createMockOrganizer();
        User userParticipant = createMockUser();
        Set<Integer> jury = Set.of(user.getId());
        Set<Integer> participants = Set.of(userParticipant.getId());

        Mockito.when(contestRepository.getByTitle(contest.getTitle())).thenThrow(EntityNotFoundException.class);

        Mockito.when(userRepository.getOrganizers()).thenReturn(List.of(organizer));

        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        Mockito.when(imgurService.uploadImageToImgurAndReturnUrl(Optional.empty(),Optional.empty())).thenReturn("");

        Mockito.when(userRepository.getById(userParticipant.getId())).thenReturn(userParticipant);

        Mockito.when(contestRepository.create(contest)).thenReturn(contest);

        contestService.create(organizer,contest,jury,participants,Optional.empty(),Optional.empty());

        Mockito.verify(contestRepository,Mockito.times(1)).create(contest);
    }

    @Test
    public void setContestJury_Should_Return_When_IsCalled() {

    }





}
