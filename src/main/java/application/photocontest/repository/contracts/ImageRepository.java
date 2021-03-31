package application.photocontest.repository.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.ImageRating;

public interface ImageRepository extends CudRepositoryOperations<Image>{

    Image getById(int id);


    void createJurorRateEntity(ImageRating imageRating);


}
