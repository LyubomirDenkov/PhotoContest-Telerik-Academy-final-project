package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.models.Contest;
import application.photocontest.models.Image;
import application.photocontest.models.dto.ImageRankingDto;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class AsynchronousTaskScheduler implements Runnable {

    private final int DEFAULT_SCORE = 3;

    private final ContestRepository contestRepository;
    private final ImageRepository imageRepository;


    @Autowired
    public AsynchronousTaskScheduler(ContestRepository contestRepository, ImageRepository imageRepository) {
        this.contestRepository = contestRepository;
        this.imageRepository = imageRepository;
    }


    @Override
    @Scheduled(fixedDelay = 60000)
    public void run() {

        List<Contest> contests = contestRepository.getAll();
        calculateAndRewardPoints(contests.get(0));
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
        Set<Image> images = contest.getImages();

        List<ImageRankingDto> imageRankingDtoList = new ArrayList<>();


        for (Image image : images) {
            ImageRankingDto imageRankingDto = new ImageRankingDto();

            long imagePointsFromReviews = imageRepository.getReviewPointsByImageId(image.getId());
            long imageReviewsCount = imageRepository.getReviewsCountByContestAndImageId(contest.getId(),image.getId());
            long notReviewedCount = 0;

            if (imageReviewsCount < juryCount){
                notReviewedCount = juryCount - imageReviewsCount;
            }

            int pointsToAwardForNotReviewed = (int) (notReviewedCount * DEFAULT_SCORE);
            imageRankingDto.setImage(image.getId());
            imageRankingDto.setPoints(imagePointsFromReviews + pointsToAwardForNotReviewed);

            imageRankingDtoList.add(imageRankingDto);

        }
        imageRankingDtoList.sort(Comparator.comparing(ImageRankingDto::getPoints).reversed());

        List<ImageRankingDto> firstPlace = new ArrayList<>();
        List<ImageRankingDto> secondPlace = new ArrayList<>();
        List<ImageRankingDto> thirdPlace = new ArrayList<>();

        for (int i = 0; i < imageRankingDtoList.size() + 1; i++) {

        }
    }
}
