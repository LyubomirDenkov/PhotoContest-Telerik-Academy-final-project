package application.photocontest.modelmappers;
import application.photocontest.models.ImageReview;
import application.photocontest.models.dto.ImageReviewDto;

import org.springframework.stereotype.Component;

@Component
public class ImageReviewMapper {




    public ImageReviewMapper() {

    }



    public ImageReview fromDto(ImageReviewDto imageReviewDto) {
        ImageReview imageReview = new ImageReview();

        imageReview.setComment(imageReviewDto.getComment());
        imageReview.setPoints(imageReviewDto.getPoints());

        return imageReview;
    }


}
