package application.photocontest;

import application.photocontest.models.*;

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
        return null;
    }

}
