package application.photocontest;

import application.photocontest.models.*;

import java.time.LocalDateTime;
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
        return user;
    }

    public static Contest createMockContest(){
        User organizer = createMockUser();
        organizer.setRoles(Set.of(new Role(2,"organizer")));
        Category category = createMockCategory();
        category.setName("Nature");
        Type type = new Type(1,"open");
        Contest contest = new Contest();

        contest.setTitle("Nature Photos");
        contest.setCategory(category);
        contest.setTimeTillVoting(java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        contest.setTimeTillFinished(java.sql.Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
        contest.setUser(organizer);
        contest.setType(type);
        contest.setBackgroundImage("https://i.imgur.com/JD4Auj5.png");
        contest.setPhase(new Phase(1,"ongoing"));

        return contest;
    }

}
