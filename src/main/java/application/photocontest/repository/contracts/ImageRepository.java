package application.photocontest.repository.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.ImageReview;
import application.photocontest.repository.contracts.genericrepository.CudRepositoryOperations;

import java.util.List;


public interface ImageRepository extends CudRepositoryOperations<Image> {

    Image getById(int id);

    List<Image> latestWinnerImages();

}
