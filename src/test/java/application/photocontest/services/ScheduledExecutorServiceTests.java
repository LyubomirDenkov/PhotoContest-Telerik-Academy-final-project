package application.photocontest.services;


import application.photocontest.models.Contest;
import application.photocontest.models.Phase;
import application.photocontest.repository.contracts.*;
import application.photocontest.service.ScheduledExecutorService;
import application.photocontest.service.contracts.ImageService;
import application.photocontest.service.contracts.ImgurService;
import application.photocontest.service.contracts.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static application.photocontest.Helpers.createMockContest;

@ExtendWith(MockitoExtension.class)
public class ScheduledExecutorServiceTests {

    @Mock
    ContestRepository contestRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ImgurService imgurService;

    @Mock
    PhaseRepository phaseRepository;

    @Mock
    PointsRepository pointsRepository;

    @Mock
    ImageReviewRepository imageReviewRepository;

    @Mock
    NotificationService notificationService;


    @InjectMocks
    ScheduledExecutorService scheduledExecutorService;

    @Test
    public void run_Should_Run_For_Contests() {

        scheduledExecutorService.run();

        Mockito.verify(contestRepository,Mockito.times(1)).getAll();

    }

    @Test
    public void changeContestPhase_Should_Run_For_Contests() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Contest contest = createMockContest();
        Phase phase = new Phase();
        phase.setId(1);
        phase.setName("finished");



        Mockito.when(contestRepository.getAll()).thenReturn(List.of(contest));

        scheduledExecutorService.run();

        Mockito.verify(contestRepository,Mockito.times(1)).getAll();

    }

}
