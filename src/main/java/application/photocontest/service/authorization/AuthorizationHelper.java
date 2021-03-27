package application.photocontest.service.authorization;


import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;

public class AuthorizationHelper {

    public static final String EMPLOYEE_AUTHORIZATION_ERROR_MESSAGE =
            "Only an Employee is Authorized for this operation.";
    public static final String EMPLOYEE_CUSTOMER_AUTHORIZATION_ERROR_MESSAGE =
            "Only employee or customer is authorized to make this operation.";

    public static void verifyUserIsAuthorized(User user) {

        if (!user.isAdmin()) {

            throw new UnauthorizedOperationException(EMPLOYEE_AUTHORIZATION_ERROR_MESSAGE);

        }
    }

    public static void verifyUserIsCustomerOrEmployee(User user) {

        if (!user.isAdmin() && !user.isUser()) {

            throw new UnauthorizedOperationException(EMPLOYEE_CUSTOMER_AUTHORIZATION_ERROR_MESSAGE);

        }
    }
}
