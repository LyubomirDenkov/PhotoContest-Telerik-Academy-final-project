package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Category;
import application.photocontest.models.Contest;
import application.photocontest.models.UserCredentials;
import application.photocontest.service.contracts.ContestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/contests")
public class ContestsMvcController {


    private final AuthenticationHelper authenticationHelper;
    private final ContestService contestService;

    public ContestsMvcController(AuthenticationHelper authenticationHelper, ContestService contestService) {
        this.authenticationHelper = authenticationHelper;
        this.contestService = contestService;
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model, HttpSession session) {
        UserCredentials userCredentials = authenticationHelper.tryGetUser(session);
        Contest contest = contestService.getById(userCredentials, id);
        model.addAttribute("contest", contest);
        return "contest";
    }


    @GetMapping
    public String getAllCategories(Model model, HttpSession session) {
        try {

            UserCredentials currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("contests", contestService.getAll(currentUser));
            model.addAttribute("currentUser", currentUser);
            return "contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }
}
