package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.service.contracts.CredentialsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserMvcController {

    private final CredentialsService credentialsService;

    public UserMvcController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }



}
