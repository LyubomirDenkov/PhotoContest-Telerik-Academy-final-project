package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.ContestMapper;
import application.photocontest.models.Category;
import application.photocontest.models.Contest;
import application.photocontest.models.Type;
import application.photocontest.models.User;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.service.contracts.CategoryService;
import application.photocontest.service.contracts.ContestService;
import application.photocontest.service.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/contests")
public class ContestsMvcController {


    private final AuthenticationHelper authenticationHelper;
    private final ContestService contestService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ContestMapper contestMapper;

    public ContestsMvcController(AuthenticationHelper authenticationHelper, ContestService contestService, CategoryService categoryService, UserService userService, ContestMapper contestMapper) {
        this.authenticationHelper = authenticationHelper;
        this.contestService = contestService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.contestMapper = contestMapper;
    }


    @ModelAttribute("categories")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @ModelAttribute("jury")
    public List<User> getAllPotentialJury() {
        return userService.getAllPotentialJury();
    }

    @ModelAttribute("participants")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ModelAttribute("types")
    public List<Type> getAllTypes() {
        return contestService.getAllTypes();
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model, HttpSession session) {

        User user = authenticationHelper.tryGetUser(session);
        Contest contest = contestService.getById(user, id);

        model.addAttribute("currentUser",user);
        model.addAttribute("contest", contest);

        return "contest";
    }


    @GetMapping
    public String getAllContests(Model model, HttpSession session) {
        try {

            User currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("contests", contestService.getAll());
            model.addAttribute("currentUser", currentUser);
            return "contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }

    @GetMapping("/finished")
    public String getFinishedContests(Model model, HttpSession session) {
        try {

            User currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("contests", contestService.getAll());
            model.addAttribute("currentUser", currentUser);
            return "finished-contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }



    @GetMapping("/new")
    public String showNewContestPage(Model model, HttpSession session) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            isOrganizer(currentUser);

            model.addAttribute("contest", new ContestDto());
            return "contest-new";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }

    @PostMapping("/new")
    public String handleNewContestPage(@Valid @ModelAttribute("contest") ContestDto contestDto, HttpSession session) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            isOrganizer(currentUser);

            Contest contest = contestMapper.fromDto(contestDto,currentUser);
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            contestService.create(currentUser,contest,jurySet,participantsSet);

            return "redirect:/contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditContestPage(@PathVariable int id, Model model, HttpSession session) {

        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            isOrganizer(currentUser);
            Contest contest = contestService.getById(currentUser,id);

            ContestDto contestDto = contestMapper.toDto(contest);
            model.addAttribute("contestId", id);
            model.addAttribute("contest", contestDto);
            return "contest-update";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }

    @PostMapping("/{id}/update")
    public String updateContest(@PathVariable int id,
                                  @Valid @ModelAttribute("contest") ContestDto contestDto,
                                  BindingResult errors,
                                  Model model, HttpSession session) {

        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            isOrganizer(currentUser);
            if (errors.hasErrors()) {
                return "contest-update";
            }
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            Contest contest = contestMapper.fromDto(id, contestDto);
            contestService.update(currentUser,contest,jurySet,participantsSet);

            return "redirect:/contests";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }


    private void isOrganizer(User user) {
        if (!user.isOrganizer()) {
            throw new UnauthorizedOperationException("Not authorized");
        }
    }

}
