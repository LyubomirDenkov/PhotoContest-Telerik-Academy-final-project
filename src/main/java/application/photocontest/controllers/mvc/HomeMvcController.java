package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.service.contracts.ContestService;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final ImageService imageService;
    private final ContestService contestService;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public HomeMvcController(ImageService imageService, ContestService contestService, AuthenticationHelper authenticationHelper) {
        this.imageService = imageService;
        this.contestService = contestService;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping
    public String showHomePage(Model model, HttpSession session){

        UserCredentials currentUser = null;
        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("currentUser", currentUser);
        }

        model.addAttribute("contests", contestService.getOngoingContests());
        model.addAttribute("topImages", imageService.getTopEightRatedPictures());

        return "index";
    }



}
