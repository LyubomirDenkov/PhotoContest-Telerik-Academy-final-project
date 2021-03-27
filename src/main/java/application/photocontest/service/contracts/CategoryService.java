package application.photocontest.service.contracts;

import application.photocontest.models.Category;

public interface CategoryService extends GetServiceOperations<Category> {
    Category create(Category category);
}
