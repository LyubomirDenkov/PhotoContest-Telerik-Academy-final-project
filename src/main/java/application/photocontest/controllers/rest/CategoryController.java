package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.CategoryMapper;
import application.photocontest.models.Category;
import application.photocontest.models.dto.CategoryDto;
import application.photocontest.models.User;
import application.photocontest.service.contracts.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final AuthenticationHelper authenticationHelper;
    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryController(CategoryService categoryService,
                              AuthenticationHelper authenticationHelper, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.authenticationHelper = authenticationHelper;
        this.categoryMapper = categoryMapper;
    }


    @ApiOperation(value = "Get all categories")
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @ApiOperation(value = "Get by id")
    @GetMapping("/{id}")
    public Category getById(@RequestHeader HttpHeaders headers,@PathVariable int id) {

        User user = authenticationHelper.tryGetUser(headers);
        return categoryService.getById(user, id);
    }


    @ApiOperation(value = "Create category")
    @PostMapping
    public Category create(@RequestHeader HttpHeaders headers,
                           @Valid @RequestBody CategoryDto categoryDto) {

        User user = authenticationHelper.tryGetUser(headers);

        try {
            Category category = categoryMapper.fromDto(categoryDto);
            return categoryService.create(user, category);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
