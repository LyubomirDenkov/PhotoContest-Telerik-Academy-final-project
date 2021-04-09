package application.photocontest.controllers.mvc;


import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.modelmappers.UserMapper;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.LoginDto;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.service.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {


    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;



    public AuthenticationController(UserMapper userMapper, AuthenticationHelper authenticationHelper, UserService userService) {
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto login,
                              Model model,
                              BindingResult bindingResult,
                              HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            authenticationHelper.verifyAuthentication(login.getUserName(), login.getPassword());
            session.setAttribute("currentUser", login.getUserName());
            User currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
            return "redirect:/";
        } catch (AuthenticationFailureException e) {
            bindingResult.rejectValue("userName", "auth_error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "register";
    }


    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("register") RegisterDto register,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "file")Optional<MultipartFile> file,
                                 @RequestParam(name = "url") Optional<String> url) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!register.getPassword().equals(register.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "password_error", "Password confirmation should match password.");
            return "register";
        }

        try {
            User user = userMapper.fromDto(register);
            userService.create(user,file,url);
            return "redirect:/";
        } catch (DuplicateEntityException | IOException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            return "register";
        }
    }

}
