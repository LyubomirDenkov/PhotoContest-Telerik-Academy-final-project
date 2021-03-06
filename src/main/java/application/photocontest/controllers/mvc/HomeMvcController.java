package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;
import application.photocontest.service.contracts.ContestService;
import application.photocontest.service.contracts.ImageService;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static application.photocontest.constants.Constants.CURRENT_USER;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final ImageService imageService;
    private final ContestService contestService;
    private final AuthenticationHelper authenticationHelper;

    private final UserService userService;

    @Autowired
    public HomeMvcController(ImageService imageService, ContestService contestService,
                             AuthenticationHelper authenticationHelper, UserService userService) {
        this.imageService = imageService;
        this.contestService = contestService;
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }


    @GetMapping
    public String showHomePage(Model model, HttpSession session) {


        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute(CURRENT_USER, user);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute(CURRENT_USER, null);
        }

        model.addAttribute("contests", contestService.mainPageOngoingContest());
        model.addAttribute("images", imageService.latestWinnerImages());

        return "index";
    }


}
