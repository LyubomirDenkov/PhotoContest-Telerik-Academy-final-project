package application.photocontest.controllers.authentications;

import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

import static application.photocontest.constants.Constants.*;

@Component
public class AuthenticationHelper {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationHelper(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    public User tryGetUser(HttpHeaders headers) {
        unauthorizedException(headers);
        try {
            String userName = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            return userService.getUserByUserName(userName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_EMAIL_ERROR_MESSAGE);
        }
    }

    public User tryGetUser(HttpSession session) {
        String currentUserName = (String) session.getAttribute("currentUser");

        if (currentUserName == null) {
            throw new UnauthorizedOperationException(NO_USER_LOGGED_IN);
        }

        return userService.getUserByUserName(currentUserName);
    }

    public User verifyAuthentication(String userName, String password) {
        try {
            User user = userService.getUserByUserName(userName);
            if (!passwordEncoder.matches(password, user.getUserCredentials().getPassword())) {
                throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
        }
    }


    private void unauthorizedException(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AUTHORIZATION_ERROR_MESSAGE);
        }
    }
}
