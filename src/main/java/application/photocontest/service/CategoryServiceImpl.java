package application.photocontest.service;

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

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserIsAuthorized;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(User user, Category category) {

        verifyUserIsAuthorized(user);

        return categoryRepository.create(category);
    }

    @Override
    public List<Category> getAll(User user) {
        return categoryRepository.getAll();
    }

    @Override
    public Category getById(User user, int id) {

        try {
            return categoryRepository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
