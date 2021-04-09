package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.models.Contest;
import application.photocontest.models.Image;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.service.contracts.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class AsynchronousTaskScheduler implements Runnable {

    private final ContestRepository contestRepository;


    @Autowired
    public AsynchronousTaskScheduler(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }


    @Override
    @Scheduled(fixedDelay = 60000)
    public void run() {

        List<Contest> contests = contestRepository.getAll();

        LocalDateTime localDateTime = LocalDateTime.now();
        Date dateTimeNow = java.sql.Timestamp.valueOf(localDateTime);
        Date contestFirstPhaseEndDate;
        Date contestSecondPhaseEndDate;

        for (Contest contest : contests) {

            if (contest.getPhase().getName().equalsIgnoreCase(ContestPhases.ONGOING.toString())){
                contestFirstPhaseEndDate = contest.getTimeTillVoting();
                if (dateTimeNow.after(contestFirstPhaseEndDate)){
                    contest.setPhase(contestRepository.getPhaseByName(ContestPhases.VOTING.toString()));
                    contestRepository.update(contest);
                }
                continue;
            }

            if(contest.getPhase().getName().equalsIgnoreCase(ContestPhases.VOTING.toString())){

                contestSecondPhaseEndDate = contest.getTimeTillFinished();
                if (dateTimeNow.after(contestSecondPhaseEndDate)){
                    contest.setPhase(contestRepository.getPhaseByName(ContestPhases.FINISHED.toString()));
                    contestRepository.update(contest);
                    calculateAndRewardPoints(contest);
                }
            }
        }

        System.out.println("TASK DONE!");
    }

    private void calculateAndRewardPoints(Contest contest){

        int juryCount = contest.getJury().size();






    }
}
