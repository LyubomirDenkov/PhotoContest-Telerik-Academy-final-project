package application.photocontest.repository.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.ImageReview;

import java.util.List;


public interface ImageRepository extends CudRepositoryOperations<Image>{

    Image getById(int id);


    void createJurorRateEntity(ImageReview imageReview);

    Long getReviewPointsByImageId(int id);

    List<ImageReview> getImageRatingsByUsername(String userName);


}
