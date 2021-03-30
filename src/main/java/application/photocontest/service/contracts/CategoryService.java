package application.photocontest.service.contracts;

import application.photocontest.models.Category;
import application.photocontest.models.UserCredentials;

public interface CategoryService extends GetServiceOperations<Category> {
    Category create(UserCredentials user, Category category);
}
