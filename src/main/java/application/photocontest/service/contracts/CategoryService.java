package application.photocontest.service.contracts;

import application.photocontest.models.Category;
import application.photocontest.models.UserCredentials;

import java.util.List;

public interface CategoryService {
    Category create(UserCredentials user, Category category);
    List<Category> getAll();

    Category getById(UserCredentials userCredentials, int id);

}
