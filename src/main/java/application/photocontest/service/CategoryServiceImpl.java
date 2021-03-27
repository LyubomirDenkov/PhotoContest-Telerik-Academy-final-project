package application.photocontest.service;

import application.photocontest.models.Category;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.service.contracts.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category name) {
        return null;
    }

    @Override
    public Category update(Category name) {
        return null;
    }

    @Override
    public Category delete(int id) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public Category getById(int id) {
        return null;
    }
}
