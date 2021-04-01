package application.photocontest.repository.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.ImageRating;

import java.util.List;


public interface ImageRepository extends CudRepositoryOperations<Image>{

    Image getById(int id);


    void createJurorRateEntity(ImageRating imageRating);


    List<ImageRating> getImageRatingsByUsername(String userName);

}
