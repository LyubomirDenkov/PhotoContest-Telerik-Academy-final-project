package application.photocontest.controllers.mvc;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.AuthenticationFailureException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.CategoryMapper;
import application.photocontest.models.Category;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.CategoryDto;
import application.photocontest.service.contracts.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryMvcController {

    private final CategoryService categoryService;
    private final AuthenticationHelper authenticationHelper;
    private final CategoryMapper categoryMapper;


    public CategoryMvcController(CategoryService categoryService, AuthenticationHelper authenticationHelper, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.authenticationHelper = authenticationHelper;
        this.categoryMapper = categoryMapper;
    }


    @ModelAttribute("categories")
    public List<Category> getAll() {
        return categoryService.getAll();
    }


    @GetMapping
    public String getAll(Model model, HttpSession session) {
        return "categories";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("category") CategoryDto categoryDto, HttpSession session,
                         BindingResult errors) {

        try {
            UserCredentials user = authenticationHelper.tryGetUser(session);
            if (errors.hasErrors()) {
                return "category-new";
            }
            Category category = categoryMapper.fromDto(categoryDto);

            categoryService.create(user, category);

            return "redirect:/categories";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }


    }

    @GetMapping("/new")
    public String showNewCategoryPage(Model model, HttpSession session) {

        try {
            UserCredentials currentUser = authenticationHelper.tryGetUser(session);

            isOrganizer(currentUser);

            model.addAttribute("category", new CategoryDto());

            return "category-new";

        } catch (AuthenticationFailureException | UnauthorizedOperationException e) {
            return "not-found";
        }
    }
    private void isOrganizer(UserCredentials currentUser) {
        if (!currentUser.isOrganizer()) {
            throw new UnauthorizedOperationException("Not authorized");
        }
    }

}
