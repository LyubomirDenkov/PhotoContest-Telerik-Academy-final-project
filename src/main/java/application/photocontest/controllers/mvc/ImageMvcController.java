package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.User;
import application.photocontest.service.contracts.ImageReviewService;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("images")
public class ImageMvcController {


    private final ImageService imageService;
    private final ImageReviewService imageReviewService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ImageMvcController(ImageService imageService, ImageReviewService imageReviewService, AuthenticationHelper authenticationHelper) {
        this.imageService = imageService;
        this.imageReviewService = imageReviewService;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model, HttpSession session) {
        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("currentUser", currentUser);
            model.addAttribute("image", imageService.getById(id));
            model.addAttribute("reviews", imageReviewService.getAllReviewsByImageId(currentUser, id));
            return "image";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "error";
        }
    }
}
