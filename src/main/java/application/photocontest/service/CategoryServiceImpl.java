package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Category;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.service.contracts.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public Category getById(User user, int id) {

        verifyUserHasRoles(user,UserRoles.ORGANIZER);
        return categoryRepository.getById(id);

    }

    @Override
    public Category create(User user, Category category) {

        verifyUserHasRoles(user, UserRoles.ORGANIZER);
        boolean ifCategoryExists = true;
        try {
            categoryRepository.getByName(category.getName());
        } catch (EntityNotFoundException e) {
            ifCategoryExists= false;
        }
        if (ifCategoryExists) {
            throw new DuplicateEntityException("Category", "name", category.getName());
        }

        return categoryRepository.create(category);
    }
}
