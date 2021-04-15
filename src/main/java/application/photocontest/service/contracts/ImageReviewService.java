package application.photocontest.service.contracts;

import application.photocontest.models.ImageReview;
import application.photocontest.models.User;

import java.util.List;

public interface ImageReviewService {

    List<ImageReview> getAllReviewsByImageId(User user, int imageId);

}
