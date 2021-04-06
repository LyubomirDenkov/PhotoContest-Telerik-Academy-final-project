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

    public static final String EMPLOYEE_AUTHORIZATION_ERROR_MESSAGE =
            "Only an Employee is Authorized for this operation.";
    public static final String EMPLOYEE_CUSTOMER_AUTHORIZATION_ERROR_MESSAGE =
            "Only employee or customer is authorized to make this operation.";
    public static final String ORGANIZER_AUTHORIZATION_ERROR_MESSAGE =
            "Only an organizer is authorized to make this operation.";


    public static void verifyUserHasRoles(User user, UserRoles... roles){

        List<String> rolesList = Arrays.stream(roles).map(UserRoles::toString).collect(Collectors.toList());

        user.getRoles().stream()
                 .map(r -> r.getName().toLowerCase())
                 .filter(rolesList::contains)
                 .findAny()
                 .orElseThrow(() -> new UnauthorizedOperationException("Unauthorized"));
    }


    public static void verifyIsUserOwnAccount(int userId, int userToDeleteId, String message){

        if (userId != userToDeleteId){
            throw new UnauthorizedOperationException(message);
        }

    }

}
