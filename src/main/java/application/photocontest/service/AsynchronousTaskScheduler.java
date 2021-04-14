package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.PhaseRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static application.photocontest.service.constants.Constants.MAIL_TITLE_CONTEST_END;
import static application.photocontest.service.constants.Constants.MESSAGE_CONTEST_END_TOP_POSITION;

@Component
@EnableScheduling
public class AsynchronousTaskScheduler implements Runnable {

    private final String FIRST_POSITION = "first";
    private final String SECOND_POSITION = "second";
    private final String THIRD_POSITION = "third";

    private final int DEFAULT_SCORE = 3;

    private final ContestRepository contestRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final PhaseRepository phaseRepository;
    private final NotificationService notificationService;


    @Autowired
    public AsynchronousTaskScheduler(ContestRepository contestRepository, ImageRepository imageRepository,
                                     UserRepository userRepository, PhaseRepository phaseRepository,
                                     NotificationService notificationService) {
        this.contestRepository = contestRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.phaseRepository = phaseRepository;
        this.notificationService = notificationService;
    }


    @Scheduled(fixedDelay = 60000)
    public void run() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:ms")) +
                " INFO TASK START");
        List<Contest> contests = contestRepository.getAll();

        for (Contest contest : contests) {
            changeContestPhaseWhenEndPhaseDateIsReached(contest);
        }

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:ms")) +
                " INFO TASK DONE SUCCESSFULLY!!");
    }

    private void changeContestPhaseWhenEndPhaseDateIsReached(Contest contest) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Date dateTimeNow = java.sql.Timestamp.valueOf(localDateTime);
        Date contestFirstPhaseEndDate;
        Date contestSecondPhaseEndDate;

        if (contest.getPhase().getName().equalsIgnoreCase(ContestPhases.ONGOING.toString())) {
            contestFirstPhaseEndDate = contest.getTimeTillVoting();
            if (dateTimeNow.after(contestFirstPhaseEndDate)) {
                contest.setPhase(phaseRepository.getPhaseByName(ContestPhases.VOTING.toString()));
                contestRepository.update(contest);
            }
            return;
        }

        if (contest.getPhase().getName().equalsIgnoreCase(ContestPhases.VOTING.toString())) {

            contestSecondPhaseEndDate = contest.getTimeTillFinished();
            if (dateTimeNow.after(contestSecondPhaseEndDate)) {
                contest.setPhase(phaseRepository.getPhaseByName(ContestPhases.FINISHED.toString()));
                contestRepository.update(contest);
                calculateAndRewardPointsToImages(contest);
            }
        }
    }

    //TODO REFACTOR LATER - logic is work

    private void calculateAndRewardPointsToImages(Contest contest) {

        int juryCount = contest.getJury().size();
        Set<Image> images = contest.getImages();

        List<Image> imageRankingList = new ArrayList<>();

        boolean hasReviews = true;
        for (Image image : images) {

            long imageReviewsCount = 0;

            try {
                imageReviewsCount = imageRepository.getReviewsCountByContestAndImageId(contest.getId(), image.getId());
            } catch (EntityNotFoundException e) {
                hasReviews = false;
            }
            long notReviewedCount = 0;

            if (imageReviewsCount < juryCount) {
                notReviewedCount = juryCount - imageReviewsCount;
            }

            int pointsToAwardForNotReviewed = (int) (notReviewedCount * DEFAULT_SCORE);

            image.setPoints(image.getPoints() + pointsToAwardForNotReviewed);
            imageRepository.update(image);

            imageRankingList.add(image);
        }
        imageRankingList.sort(Comparator.comparing(Image::getPoints).reversed());
        calculateRewardPointsForFirstThreePlaces(imageRankingList);
    }

    private void calculateRewardPointsForFirstThreePlaces(List<Image> imageRankingList) {

        List<Image> firstPlace = new ArrayList<>();
        List<Image> secondPlace = new ArrayList<>();
        List<Image> thirdPlace = new ArrayList<>();
        int position = 1;

        boolean isFirstWithDoubleScoreThanSecond = false;
        for (int i = 0; i < imageRankingList.size() + 1; i++) {

            if (i == imageRankingList.size()){
                break;
            }

            int firstPointer = imageRankingList.get(i).getPoints();
            int secondPointer = imageRankingList.get(i + 1).getPoints();

            if (position > 3) break;

            switch (position) {
                case 1:

                    if (firstPointer > secondPointer) {

                        if (firstPointer >= (secondPointer * 2)) {
                            isFirstWithDoubleScoreThanSecond = true;
                        }

                        firstPlace.add(imageRankingList.get(i));
                        position++;
                    } else {
                        firstPlace.add(imageRankingList.get(i));
                    }

                    break;
                case 2:
                    if (firstPointer > secondPointer) {
                        secondPlace.add(imageRankingList.get(i));
                        position++;
                    } else {
                        secondPlace.add(imageRankingList.get(i));
                    }
                    break;
                case 3:
                    if (firstPointer > secondPointer) {
                        thirdPlace.add(imageRankingList.get(i));
                        position++;
                    } else {
                        thirdPlace.add(imageRankingList.get(i));
                    }
                    break;
            }
        }

        int pointsForPositionOne = 50;
        if (isFirstWithDoubleScoreThanSecond) {
            pointsForPositionOne += 25;
        }
        int pointsForPositionTwo = 35;
        int pointsForPositionThree = 20;

        rewardAndUpdateUser(pointsForPositionOne, firstPlace,FIRST_POSITION);
        rewardAndUpdateUser(pointsForPositionTwo, secondPlace,SECOND_POSITION);
        rewardAndUpdateUser(pointsForPositionThree, thirdPlace,THIRD_POSITION);

    }


    private void rewardAndUpdateUser(int pointsReward, List<Image> images,String position) {

        int pointsRewardByPosition = pointsReward;
        if (images.size() > 1) {
            pointsRewardByPosition -= 10;
        }

        for (int i = 0; i < images.size(); i++) {
            User user = userRepository.getUserByPictureId(images.get(i).getId());
            Optional<Points> points = user.getPoints().stream().findFirst();
            if (points.isEmpty()) {
                continue;
            }
            points.get().setPoints(points.get().getPoints() + pointsRewardByPosition);

            Notification notification = buildAndCreateMessage(user,pointsRewardByPosition,position);

            Set<Notification> notifications = user.getMessages();
            notifications.add(notification);
            user.setMessages(notifications);

            userRepository.update(user);
            userRepository.updatePoints(points.get());
        }
    }

    private Notification buildAndCreateMessage(User user, int points, String position){
        Notification notification = new Notification();
        notification.setTitle(String.format(MAIL_TITLE_CONTEST_END,"something"));
        notification.setMessage(String.format(MESSAGE_CONTEST_END_TOP_POSITION,user.getFirstName(),position,points));
        notification.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return notificationService.create(notification);
    }
}
