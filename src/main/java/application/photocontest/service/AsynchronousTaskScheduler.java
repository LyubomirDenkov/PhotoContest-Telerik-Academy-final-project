package application.photocontest.service;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class AsynchronousTaskScheduler implements Runnable{


    @Override
   @Scheduled(fixedDelay = 60000)
    public void run() {
        System.out.println("fatkaКЮПЮЮЮ");
    }
}
