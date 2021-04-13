package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.UserMapper;
import application.photocontest.models.User;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users")
public class UserMvcController {


    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;
    private final UserMapper userMapper;


    @Autowired
    public UserMvcController(AuthenticationHelper authenticationHelper, UserService userService, UserMapper userMapper) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getUsersLeaderboard(Model model, HttpSession session){
        User user = authenticationHelper.tryGetUser(session);
        model.addAttribute("currentUser",user);
        model.addAttribute("users",userService.getLeaderboard(user));
        return "leaderboard";
    }

    @GetMapping("/{id}/profile")
    public String getProfile(@PathVariable int id, Model model, HttpSession session) {

        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", userService.getById(user,id));
            return "profile";
        }catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "error";
        }
    }

    @GetMapping("/{id}/update")
    public String editUserProfile(@PathVariable int id, Model model,HttpSession session){

        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", userService.getById(user, id));
            model.addAttribute("updateUserDto",new UpdateUserDto());
            return "edit-profile";
        }catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "error";
        }
    }

    @PostMapping("{id}/update")
    public String handleEditUserProfile(@PathVariable int id, HttpSession session,
                                        @Valid @ModelAttribute("updateUserDto") UpdateUserDto dto,
                                        @RequestParam(value = "multiPartFile", required = false) Optional<MultipartFile> file,
                                        @RequestParam(value = "url", required = false) Optional<String> url,
                                        BindingResult bindingResult){




        try {
            User user = authenticationHelper.tryGetUser(session);
            User userToUpdate = userMapper.fromDto(id,dto);
            userService.update(user,userToUpdate,file,url);
            return "redirect:/users/{id}/profile";
        }catch (AuthenticationFailureException | UnauthorizedOperationException | IOException e) {
            return "error";
        }

    }

}
