package application.photocontest.modelmappers;
import application.photocontest.models.ImageReview;
import application.photocontest.models.dto.ImageReviewDto;
import application.photocontest.repository.contracts.ContestRepository;
import org.springframework.stereotype.Component;

@Component
public class ImageReviewMapper {

    private final ContestRepository contestRepository;


    public ImageReviewMapper(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }



    public ImageReview fromDto(ImageReviewDto imageReviewDto) {
        ImageReview imageReview = new ImageReview();

        imageReview.setComment(imageReviewDto.getComment());
        imageReview.setPoints(imageReviewDto.getPoints());

        return imageReview;
    }


}
