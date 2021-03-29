package application.photocontest.controllers.mvc;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final ImageService imageService;


    @Autowired
    public HomeMvcController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping
    public String homePage(Model model) {

        User user = new User();
        Image image = imageService.getById(user,2);

        model.addAttribute("images", image.getImageData());

        return "index";
    }

}
