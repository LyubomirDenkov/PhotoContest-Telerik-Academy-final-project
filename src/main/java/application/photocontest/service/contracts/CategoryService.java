package application.photocontest.service.contracts;

import application.photocontest.models.Category;
import application.photocontest.models.User;

public interface CategoryService extends GetServiceOperations<Category> {
    Category create(User user, Category category);
}
