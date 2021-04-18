package application.photocontest.service;

import application.photocontest.enums.ContestPhases;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static application.photocontest.service.helper.NotificationHelper.buildAndCreateNotification;

@Component
@EnableScheduling
public class ScheduledExecutorService implements Runnable {

    private final String FIRST_POSITION = "first";
    private final String SECOND_POSITION = "second";
    private final String THIRD_POSITION = "third";

    private final int DEFAULT_SCORE = 3;

    private final ContestRepository contestRepository;
    private final ImageReviewRepository imageReviewRepository;
    private final UserRepository userRepository;
    private final PhaseRepository phaseRepository;
    private final PointsRepository pointsRepository;


    @Autowired
    public ScheduledExecutorService(ContestRepository contestRepository,
                                    ImageReviewRepository imageReviewRepository, UserRepository userRepository, PhaseRepository phaseRepository, PointsRepository pointsRepository) {
        this.contestRepository = contestRepository;
        this.imageReviewRepository = imageReviewRepository;
        this.userRepository = userRepository;
        this.phaseRepository = phaseRepository;
        this.pointsRepository = pointsRepository;
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

    private void calculateAndRewardPointsToImages(Contest contest) {

        int juryCount = contest.getJury().size();
        Set<Image> images = contest.getImages();

        Map<Image, Integer> map = new HashMap<>();

        for (Image image : images) {
            boolean hasReviews = true;
            long imageReviewsCount = 0;
            long imageReviewsPoints = 0;

            try {
                imageReviewsCount = imageReviewRepository.getReviewsCountByContestAndImageId(contest.getId(), image.getId());
                Long points = imageReviewRepository.getImageReviewPointsByContestAndImageId(contest.getId(), image.getId());

                if (points != null) {
                    imageReviewsPoints += points;
                }

            } catch (EntityNotFoundException e) {
                hasReviews = false;
            }
            long notReviewedCount = 0;

            if (hasReviews) {
                if (imageReviewsCount < juryCount) {
                    notReviewedCount = juryCount - imageReviewsCount;
                }
            } else {
                notReviewedCount = juryCount;
            }

            int pointsToAwardForNotReviewed = (int) (notReviewedCount * DEFAULT_SCORE) + (int) imageReviewsPoints;

            map.put(image, pointsToAwardForNotReviewed);
        }

        if (map.isEmpty()) {
            return;
        }

        Map<Image, Integer> sortedMap = map.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));

        calculateRewardPointsForFirstThreePlaces(sortedMap, contest.getTitle());
    }


    private void calculateRewardPointsForFirstThreePlaces(Map<Image, Integer> map, String contestTitle) {

        List<Image> firstPlace = new ArrayList<>();
        List<Image> secondPlace = new ArrayList<>();
        List<Image> thirdPlace = new ArrayList<>();
        int[] position = new int[1];
        position[0] = 1;

        int previousImagePoints = 0;
        boolean isFirstWithDoubleScoreThanSecond = false;
        boolean isFirstWithDoubleThanSecondChecked = false;

        for (Map.Entry<Image, Integer> imageIntegerEntry : map.entrySet()) {


            if (imageIntegerEntry.getValue() < previousImagePoints) {
                position[0] += 1;
            }

            if (position[0] > 3) break;

            switch (position[0]) {
                case 1:
                    firstPlace.add(imageIntegerEntry.getKey());
                    break;
                case 2:

                    int currentPoints = imageIntegerEntry.getValue();

                    if (!isFirstWithDoubleThanSecondChecked) {

                        if ((currentPoints * 2) < previousImagePoints) {
                            isFirstWithDoubleScoreThanSecond = true;
                        }
                        isFirstWithDoubleThanSecondChecked = true;
                    }

                    secondPlace.add(imageIntegerEntry.getKey());

                    break;
                case 3:
                    thirdPlace.add(imageIntegerEntry.getKey());
                    break;
            }
            previousImagePoints = imageIntegerEntry.getValue();
        }

        int pointsForPositionOne = 50;
        if (isFirstWithDoubleScoreThanSecond) {
            pointsForPositionOne += 25;
        }
        int pointsForPositionTwo = 35;
        int pointsForPositionThree = 20;

        rewardAndUpdateUser(pointsForPositionOne, firstPlace, FIRST_POSITION, contestTitle);
        rewardAndUpdateUser(pointsForPositionTwo, secondPlace, SECOND_POSITION, contestTitle);
        rewardAndUpdateUser(pointsForPositionThree, thirdPlace, THIRD_POSITION, contestTitle);

    }

    private void rewardAndUpdateUser(int pointsReward, List<Image> images, String position, String contestTitle) {

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

            Notification notification = buildAndCreateNotification(user, pointsRewardByPosition, position, contestTitle);

            Set<Notification> notifications = user.getNotifications();
            notifications.add(notification);
            user.setNotifications(notifications);

            userRepository.update(user);
            pointsRepository.update(points.get());
        }
    }

}
