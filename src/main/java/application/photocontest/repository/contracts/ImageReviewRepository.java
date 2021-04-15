package application.photocontest.repository.contracts;

import application.photocontest.models.ImageReview;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageReviewRepository {

    void create(ImageReview imageReview);


    List<ImageReview> getImageReviewByUserId(int id);

    ImageReview getImageReviewUserContestAndImageId(int userId,int contestId,int imageId);

    Long getImageReviewPointsByContestAndImageId(int contestId, int imageId);

    Long getReviewsCountByContestAndImageId(int contestId,int imageId);
}
