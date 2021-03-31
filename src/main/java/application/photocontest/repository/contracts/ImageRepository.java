package application.photocontest.repository.contracts;

import application.photocontest.models.Image;

public interface ImageRepository extends CudRepositoryOperations<Image>{

    Image getById(int id);


}
