package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.models.Contest;
import application.photocontest.models.Image;
import application.photocontest.models.Points;
import application.photocontest.models.User;
import application.photocontest.models.dto.ImageRankingDto;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@EnableScheduling
public class AsynchronousTaskScheduler implements Runnable {

    private final int DEFAULT_SCORE = 3;

    private final ContestRepository contestRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;


    @Autowired
    public AsynchronousTaskScheduler(ContestRepository contestRepository, ImageRepository imageRepository, UserRepository userRepository) {
        this.contestRepository = contestRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
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
                    //calculateAndRewardPoints(contest);
                }
            }
        }

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:ms")) +
                " INFO TASK DONE SUCCESSFULLY!!");
    }

    //TODO REFACTOR LATER - logic is work

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
        int position = 1;

        boolean isFirstWithDoubleScoreThanSecond = false;
        for (int i = 0; i < imageRankingDtoList.size() + 1; i++) {
            if (position > 3) break;

            switch (position){
                case 1:
                    if (imageRankingDtoList.get(i).getPoints() > imageRankingDtoList.get(i + 1).getPoints()){

                        if (imageRankingDtoList.get(i).getPoints() >= (imageRankingDtoList.get(i + 1).getPoints() * 2)){
                            isFirstWithDoubleScoreThanSecond = true;
                        }

                        firstPlace.add(imageRankingDtoList.get(i));
                        position++;
                    }else {
                        firstPlace.add(imageRankingDtoList.get(i));
                    }
                    break;
                case 2:
                    if (imageRankingDtoList.get(i).getPoints() > imageRankingDtoList.get(i + 1).getPoints()){
                        secondPlace.add(imageRankingDtoList.get(i));
                        position++;
                    }else {
                        secondPlace.add(imageRankingDtoList.get(i));
                    }
                    break;
                case 3:
                    if (imageRankingDtoList.get(i).getPoints() > imageRankingDtoList.get(i + 1).getPoints()){
                        thirdPlace.add(imageRankingDtoList.get(i));
                        position++;
                    }else {
                        thirdPlace.add(imageRankingDtoList.get(i));
                    }
                    break;
            }
        }

        int pointsForPositionOne = 50;
        if (isFirstWithDoubleScoreThanSecond){
            pointsForPositionOne += 25;
        }
        if (firstPlace.size() > 1){
            pointsForPositionOne -= 10;
        }
        for (int i = 0; i < firstPlace.size(); i++) {
            User user = userRepository.getUserByPictureId(firstPlace.get(i).getImage());
            Optional<Points> points = user.getPoints().stream().findFirst();
            points.get().setPoints(points.get().getPoints() + pointsForPositionOne);
            userRepository.updatePoints(points.get());
        }
        int pointsForPositionTwo = 35;
        if (secondPlace.size() > 1){
            pointsForPositionTwo -= 10;
        }
        for (int i = 0; i < secondPlace.size(); i++) {
            User user = userRepository.getUserByPictureId(secondPlace.get(i).getImage());
            Optional<Points> points = user.getPoints().stream().findFirst();
            points.get().setPoints(points.get().getPoints() + pointsForPositionTwo);
            userRepository.updatePoints(points.get());
        }
        int pointsForPositionThree = 20;
        if (secondPlace.size() > 1){
            pointsForPositionThree -= 10;
        }
        for (int i = 0; i < thirdPlace.size(); i++) {
            User user = userRepository.getUserByPictureId(thirdPlace.get(i).getImage());
            Optional<Points> points = user.getPoints().stream().findFirst();
            points.get().setPoints(points.get().getPoints() + pointsForPositionThree);
            userRepository.updatePoints(points.get());
        }
    }
}
