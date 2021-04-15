package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.ImageReview;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageReviewRepository;
import application.photocontest.service.contracts.ImageReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class ImageReviewServiceImpl implements ImageReviewService {

    private final ImageReviewRepository imageReviewRepository;

    @Autowired
    public ImageReviewServiceImpl(ImageReviewRepository imageReviewRepository) {
        this.imageReviewRepository = imageReviewRepository;
    }


    @Override
    public List<ImageReview> getAllReviewsByImageId(User user, int imageId) {

        verifyUserHasRoles(user, UserRoles.USER);

        return imageReviewRepository.getImageReviewByImageId(imageId);
    }
}
