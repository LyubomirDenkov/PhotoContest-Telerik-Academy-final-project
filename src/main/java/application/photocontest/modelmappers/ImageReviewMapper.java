package application.photocontest.modelmappers;

import application.photocontest.models.ImageReview;
import application.photocontest.models.dto.ImageReviewDto;
import application.photocontest.repository.contracts.ContestRepository;

public class ImageReviewMapper {

    private final ContestRepository contestRepository;


    public ImageReviewMapper(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

}
