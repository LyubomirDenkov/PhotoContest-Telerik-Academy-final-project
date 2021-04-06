package application.photocontest.service.contracts;

import application.photocontest.models.Category;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;

import java.util.List;

public interface CategoryService {
    Category create(User user, Category category);
    List<Category> getAll();

    Category getById(User user, int id);

}
