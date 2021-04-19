package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;
import application.photocontest.service.contracts.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static application.photocontest.constants.Constants.CURRENT_USER;

@Controller
@RequestMapping("/notifications")
public class NotificationMvcController {


    private final NotificationService notificationService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public NotificationMvcController(NotificationService notificationService, AuthenticationHelper authenticationHelper) {
        this.notificationService = notificationService;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping
    public String getAll(Model model, HttpSession session) {

        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute(CURRENT_USER, user);
            model.addAttribute("notifications", notificationService.getAll(user));
            return "notifications";
        } catch (UnauthorizedOperationException e) {
            return "error";
        }
    }

}
