package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.ContestMapper;
import application.photocontest.modelmappers.ImageMapper;
import application.photocontest.modelmappers.ImageReviewMapper;
import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.models.dto.ImageDto;
import application.photocontest.models.dto.ImageReviewDto;
import application.photocontest.service.contracts.CategoryService;
import application.photocontest.service.contracts.ContestService;
import application.photocontest.service.contracts.ImageService;
import application.photocontest.service.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/contests")
public class ContestsMvcController {


    private final AuthenticationHelper authenticationHelper;
    private final ContestService contestService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ContestMapper contestMapper;
    private final ImageService imageService;
    private final ImageReviewMapper imageReviewMapper;
    private final ImageMapper imageMapper;

    public ContestsMvcController(AuthenticationHelper authenticationHelper, ContestService contestService,
                                 CategoryService categoryService, UserService userService, ContestMapper contestMapper, ImageService imageService, ImageReviewMapper imageReviewMapper, ImageMapper imageMapper) {
        this.authenticationHelper = authenticationHelper;
        this.contestService = contestService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.contestMapper = contestMapper;
        this.imageService = imageService;
        this.imageReviewMapper = imageReviewMapper;
        this.imageMapper = imageMapper;
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

        model.addAttribute("currentUser", user);
        model.addAttribute("contest", contest);
        model.addAttribute("imageDto",new ImageDto());

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
            return "error";
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
            return "error";
        }
    }

    @GetMapping("/voting")
    public String getVotingContests(Model model, HttpSession session) {
        try {

            User currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("contests", contestService.getAll());
            model.addAttribute("currentUser", currentUser);
            return "voting-contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "error";
        }
    }

    @GetMapping("/ongoing")
    public String getOngoingContests(Model model, HttpSession session) {
        try {

            User currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("contests", contestService.getAll());
            model.addAttribute("currentUser", currentUser);
            return "contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "error";
        }
    }

    @GetMapping("/own")
    public String getOwnContests(Model model, HttpSession session) {
        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            model.addAttribute("contests", contestService.getAll());
            model.addAttribute("currentUser", currentUser);
            return "contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "error";
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
            return "error";
        }
    }

    @PostMapping("/new")
    public String handleNewContestPage(@Valid @ModelAttribute("contest") ContestDto contestDto, HttpSession session,
                                       @RequestParam(value = "multiPartFile", required = false) Optional<MultipartFile> file,
                                       @RequestParam(value = "url", required = false) Optional<String> url) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            isOrganizer(currentUser);

            Contest contest = contestMapper.fromDto(contestDto, currentUser);
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            contestService.create(currentUser, contest, jurySet, participantsSet,file,url);

            return "redirect:/contests";

        } catch (AuthenticationFailureException | UnauthorizedOperationException | IOException e) {
            return "error";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditContestPage(@PathVariable int id, Model model, HttpSession session) {

        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            isOrganizer(currentUser);
            Contest contest = contestService.getById(currentUser, id);

            ContestDto contestDto = contestMapper.toDto(contest);
            model.addAttribute("contestId", id);
            model.addAttribute("contest", contestDto);
            return "contest-update";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "error";
        }
    }

    @PostMapping("/{id}/update")
    public String updateContest(@PathVariable int id,
                                @Valid @ModelAttribute("contest") ContestDto contestDto,
                                BindingResult errors,
                                Model model, HttpSession session,
                                @RequestParam(value = "multiPartFile", required = false) Optional<MultipartFile> file,
                                @RequestParam(value = "url", required = false) Optional<String> url) {

        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            isOrganizer(currentUser);
            if (errors.hasErrors()) {
                return "contest-update";
            }
            Set<Integer> jurySet = contestDto.getJury();
            Set<Integer> participantsSet = contestDto.getParticipants();
            Contest contest = contestMapper.fromDto(id, contestDto);
            contestService.update(currentUser, contest, jurySet, participantsSet,file,url);

            return "redirect:/contests";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException | IOException e) {
            return "error";
        }
    }

    @GetMapping("/{id}/user/{userId}")
    public String addUserToContest(@PathVariable int id, @PathVariable int userId, HttpSession session, Model model) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            isUser(currentUser);

            contestService.addUserToContest(currentUser, id, userId);

            return "redirect:/contests/{id}";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "error";
        } catch (DuplicateEntityException e) {
            model.addAttribute("not-found", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{contestId}/images")
    public String showContestImages(@PathVariable int contestId, HttpSession session, Model model) {

        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            Contest contest = contestService.getById(currentUser, contestId);

            isJury(currentUser, contest);

            model.addAttribute("contest", contest);

            return "contest-images";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "error";
        } catch (DuplicateEntityException e) {
            model.addAttribute("not-found", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{contestId}/images/{imageId}")
    public String showRateImagePage(@PathVariable int contestId, @PathVariable int imageId, HttpSession session, Model model) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);

            Contest contest = contestService.getById(currentUser, contestId);

            isJury(currentUser, contest);

            Image image = imageService.getById(currentUser, imageId);

            model.addAttribute("imageReview", new ImageReviewDto());
            model.addAttribute("image", image);

            return "image-review";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "not-found";
        } catch (DuplicateEntityException e) {
            model.addAttribute("not-found", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{contestId}/images/{imageId}")
    public String rateImage(@PathVariable int contestId,
                            @PathVariable int imageId,
                            @Valid @ModelAttribute("imageReview") ImageReviewDto imageReviewDto,
                            BindingResult errors, HttpSession session, Model model) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            if (errors.hasErrors()) {
                return "contest-images";
            }

            Contest contest = contestService.getById(currentUser,contestId);
            isJury(currentUser, contest);

            int points = imageReviewDto.getPoints();
            String comment = imageReviewDto.getComment();

            contestService.rateImage(currentUser, contestId, imageId, points, comment);

            return "redirect:/contests/{contestId}/images";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException e) {
            return "not-found";
        } catch (DuplicateEntityException e) {
            model.addAttribute("not-found", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{id}/upload")
    public String uploadImageToContest(@PathVariable int id, BindingResult errors,
                                       HttpSession session, Model model,
                                       @ModelAttribute("imageDto")ImageDto dto){

        if (errors.hasErrors()){
            return "index";
        }

        try {

            User currentUser = authenticationHelper.tryGetUser(session);
            Image image = imageMapper.fromDto(currentUser,dto);
            contestService.uploadImageToContest(currentUser,image,id,Optional.empty(),Optional.empty());

            return "redirect:/contests/{id}";
        } catch (AuthenticationFailureException | EntityNotFoundException | UnauthorizedOperationException | IOException e) {
            return "error";
        } catch (DuplicateEntityException e) {
            model.addAttribute("not-found", e.getMessage());
            return "error";
        }

    }


    private void isOrganizer(User user) {
        if (!user.isOrganizer()) {
            throw new UnauthorizedOperationException("Not authorized");
        }
    }

    private void isUser(User currentUser) {
        if (!currentUser.isUser()) {
            throw new UnauthorizedOperationException("Not authorized");
        }
    }

    private void isJury(User currentUser, Contest contest) {
        if (!contest.getJury().contains(currentUser)) {
            throw new UnauthorizedOperationException("Not authorized");
        }
    }
}
