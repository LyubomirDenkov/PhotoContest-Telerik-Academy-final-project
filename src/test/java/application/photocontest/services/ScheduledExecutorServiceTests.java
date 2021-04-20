package application.photocontest.services;


import application.photocontest.models.*;
import application.photocontest.repository.contracts.*;
import application.photocontest.service.ScheduledExecutorService;
import application.photocontest.service.contracts.ImgurService;
import application.photocontest.service.contracts.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static application.photocontest.Helpers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledExecutorServiceTests {


    @Mock
    ContestRepository contestRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    NotificationRepository notificationRepository;

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

        verify(contestRepository, times(1)).getAll();

    }

    @Test
    public void changeContestPhase_Should_Run_For_Contests() {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = createMockUser();
        user.setNotifications(new HashSet<>());
        Contest contest = createMockContest();
        Image image = createMockImage();

        Points points = new Points();
        points.setId(1);
        points.setPoints(10);
        user.setPoints(Set.of(points));

        contest.setTimeTillFinished(java.sql.Timestamp.valueOf(localDateTime.minusMinutes(5)));

        Phase phase = new Phase();
        phase.setId(1);
        phase.setName("voting");

        contest.setPhase(phase);
        contest.setImages(Set.of(image));

        when(contestRepository.getAll()).thenReturn(List.of(contest));

        when(userRepository.getUserByPictureId(1)).thenReturn(user);

        scheduledExecutorService.run();

        verify(pointsRepository, times(1)).update(points);

    }


}
