package application.photocontest.service.authorization;


import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Role;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationHelper {


    private static final String UNAUTHORIZED = "Unauthorized";

    public static void verifyUserHasRoles(User user, UserRoles... roles){

        List<String> rolesList = Arrays.stream(roles).map(UserRoles::toString).collect(Collectors.toList());

        user.getRoles().stream()
                 .map(r -> r.getName().toLowerCase())
                 .filter(rolesList::contains)
                 .findAny()
                 .orElseThrow(() -> new UnauthorizedOperationException(UNAUTHORIZED));
    }


    public static void verifyIsUserOwnAccount(int userId, int userToDeleteId, String message){

        if (userId != userToDeleteId){
            throw new UnauthorizedOperationException(message);
        }

    }

}
