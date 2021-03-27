package application.photocontest.repository.contracts;

import application.photocontest.models.Category;

public interface CategoryRepository extends GetOperations<Category>, CudOperations<Category> {


}
