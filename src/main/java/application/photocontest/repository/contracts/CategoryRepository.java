package application.photocontest.repository.contracts;

import application.photocontest.models.Category;
import application.photocontest.repository.contracts.genericrepository.GetRepositoryOperations;

public interface CategoryRepository extends GetRepositoryOperations<Category> {
    Category create(Category category);

    Category getByName(String name);
}
