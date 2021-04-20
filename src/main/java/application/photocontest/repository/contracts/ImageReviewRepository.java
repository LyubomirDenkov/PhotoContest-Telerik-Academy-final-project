package application.photocontest.repository.contracts;

import application.photocontest.models.ImageReview;

import java.util.List;

public interface ImageReviewRepository {

    ImageReview create(ImageReview imageReview);

    List<ImageReview> getImageReviewByImageId(int id);

    ImageReview getImageReviewUserContestAndImageId(int userId, int contestId, int imageId);

    Long getImageReviewPointsByContestAndImageId(int contestId, int imageId);

    Long getReviewsCountByContestAndImageId(int contestId, int imageId);

    void delete(ImageReview imageReview);
}
