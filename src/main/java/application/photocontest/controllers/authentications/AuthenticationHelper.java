package application.photocontest.controllers.authentications;

import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Organizer;
import application.photocontest.models.UserCredentials;
import application.photocontest.service.contracts.CredentialsService;
import application.photocontest.service.contracts.OrganizerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Component
public class AuthenticationHelper {
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String AUTHORIZATION_ERROR_MESSAGE = "The requested resource requires authentication.";
    public static final String INVALID_EMAIL_ERROR_MESSAGE = "Invalid email address.";

    public static final String AUTHENTICATION_FAILURE_MESSAGE = "Wrong email or password.";

    private final CredentialsService credentialsService;
    private final OrganizerService organizerService;


    public AuthenticationHelper(CredentialsService credentialsService, OrganizerService organizerService) {
        this.credentialsService = credentialsService;
        this.organizerService = organizerService;
    }

    public UserCredentials tryGetUser(HttpHeaders headers) {
        unauthorizedException(headers);
        try {
            String userName = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            return credentialsService.getByUserName(userName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_EMAIL_ERROR_MESSAGE);
        }
    }

    public Organizer tryGetOrganizer(HttpHeaders headers) {
        unauthorizedException(headers);
        try {
            String organizerName = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            return organizerService.getByUserName(organizerName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_EMAIL_ERROR_MESSAGE);
        }
    }

    public UserCredentials tryGetUser(HttpSession session) {
        String currentUserName = (String) session.getAttribute("currentUser");

        if (currentUserName == null) {
            throw new UnauthorizedOperationException("No user logged in.");
        }

        return credentialsService.getByUserName(currentUserName);
    }

    public UserCredentials verifyAuthentication(String userName, String password) {
        try {
            UserCredentials user = credentialsService.getByUserName(userName);
            if (!user.getPassword().equals(password)) {
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
