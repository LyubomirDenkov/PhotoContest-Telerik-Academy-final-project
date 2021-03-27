package application.photocontest.controllers.rest;

import application.photocontest.models.Category;
import application.photocontest.service.contracts.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @ApiOperation(value = "Get all categories")
    @GetMapping
    public List<Category> getAll() {

        return categoryService.getAll();

    }

    @ApiOperation(value = "Get by id")
    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        return categoryService.getById(id);
    }

}
