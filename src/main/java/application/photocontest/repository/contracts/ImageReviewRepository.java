package application.photocontest.repository.contracts;

import application.photocontest.models.ImageReview;

import java.util.List;

public interface ImageReviewRepository {
    List<ImageReview> getImageRatingsByUsername(String userName);
}
