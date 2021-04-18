package application.photocontest.services;


import application.photocontest.service.ScheduledExecutorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.time.Duration;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(ScheduledExecutorService.class)
public class ScheduledExecutorServiceTests {


  /*  @SpyBean
    private ScheduledExecutorService scheduledExecutorService;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {


        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(scheduledExecutorService, atLeast(1)).run());
    }*/

}
