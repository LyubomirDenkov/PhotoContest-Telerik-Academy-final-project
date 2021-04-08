package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserMvcController {


    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;


    @Autowired
    public UserMvcController(AuthenticationHelper authenticationHelper, UserService userService) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    public String getProfile(@PathVariable int id, Model model, HttpSession session) {

        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", user);
            model.addAttribute("users",userService.getAll(user));
            return "profile";
        }catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }

}
