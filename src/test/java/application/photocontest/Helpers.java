package application.photocontest;

import application.photocontest.models.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Helpers {


    public static Category createMockCategory(){
        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        return category;
    }

    public static User createMockUser() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName("mockUsername");
        userCredentials.setPassword("mockPassword");

        User user = new User();
        user.setId(1);
        user.setFirstName("MockFirstName");
        user.setLastName("MockLastName");
        user.setUserCredentials(userCredentials);
        user.setProfileImage("https://i.imgur.com/GdDsxXO.png");
        user.setPoints(Set.of(new Points(1,160)));
        user.setRoles(Set.of(new Role(1,"user")));
        user.setNotifications(new HashSet<>());
        return user;
    }

    public static User createMockOrganizer() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName("mockOrganizer");
        userCredentials.setPassword("mockPassword");

        User user = new User();
        user.setId(2);
        user.setFirstName("MockFirstOrgName");
        user.setLastName("MockLastOrgName");
        user.setUserCredentials(userCredentials);
        user.setProfileImage("https://i.imgur.com/GdDsxXO.png");
        user.setRoles(Set.of(new Role(2,"organizer")));
        return user;
    }

    public static Contest createMockContest(){


        Category category = createMockCategory();
        category.setName("Nature");
        Type type = new Type(1,"open");
        Contest contest = new Contest();

        contest.setId(1);
        contest.setTitle("Nature Photos");
        contest.setCategory(category);
        contest.setTimeTillVoting(java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        contest.setTimeTillFinished(java.sql.Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
        contest.setUser(createMockOrganizer());
        contest.setType(type);
        contest.setBackgroundImage(createMockImage().getUrl());
        contest.setPhase(new Phase(1,"ongoing"));
        contest.setJury(Set.of(createMockOrganizer()));
        contest.setParticipants(Set.of(createMockUser()));
        contest.setImages(new HashSet<>());

        return contest;
    }

    public static Image createMockImage() {
        Image image = new Image();

        image.setId(1);
        image.setTitle("mockImage");
        image.setUrl("https://i.imgur.com/JD4Auj5.png");
        image.setUploader(createMockUser());
        image.setStory("mockStory");

        return image;
    }

    public static ImageReview createMockImageReview() {
        ImageReview imageReview = new ImageReview();


        imageReview.setId(1);
        imageReview.setImage(createMockImage());
        imageReview.setComment("new Comment");
        imageReview.setPoints(5);
        imageReview.setContest(createMockContest());
        imageReview.setUser(createMockUser());


        return imageReview;
    }

    public static Notification createMockNotification() {
        Notification notification = new Notification();
        LocalDateTime localDateTime = LocalDateTime.now();

        notification.setId(1);
        notification.setTitle("new notification");
        notification.setDate("2020-05-06:15:00");
        notification.setMessage("new message");



        return notification;
    }



}
