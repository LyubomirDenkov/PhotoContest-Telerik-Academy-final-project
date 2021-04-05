package application.photocontest.models.dto;


import application.photocontest.models.Image;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;


public class ContestDto {

    private static final String CATEGORY_ID_ERROR_MESSAGE = "Category Id must be positive.";
    private static final String TITLE_ERROR_MESSAGE = "Contest title must be between 5 and 50 symbols.";
    public static final String PHASE_ONE_SHOULD_BE_POSITIVE = "Phase one should be positive.";
    private static final String PHASE_TWO_SHOULD_BE_POSITIVE = "Phase two should be positive.";
    private static final String TYPE_ID_SHOULD_BE_POSITIVE = "Type Id should be positive.";
    private static final String BACKGROUND_IMAGE_MUST_NOT_BE_NULL = "Background image must not be null.";

    @NotNull
    @Size(min = 5, max = 50, message = TITLE_ERROR_MESSAGE)
    private String title;

    @Positive(message = CATEGORY_ID_ERROR_MESSAGE)
    private int categoryId;

    @Positive(message = PHASE_ONE_SHOULD_BE_POSITIVE)
    private int phaseOne;

    @Positive(message = PHASE_TWO_SHOULD_BE_POSITIVE)
    private int phaseTwo;

    @Positive(message = TYPE_ID_SHOULD_BE_POSITIVE)
    private int typeId;

    @NotEmpty(message = BACKGROUND_IMAGE_MUST_NOT_BE_NULL)
    private String backgroundImage;

    private Set<Integer> participants;

    private Set<Integer> jury;

    private Set<Image> images;

    public ContestDto() {
    }

    public ContestDto(String title, int categoryId, int phaseOne,
                      int phaseTwo, int typeId, String backgroundImage, Set<Integer> participants, Set<Integer> jury, Set<Image> images) {
        this.title = title;
        this.categoryId = categoryId;

        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.typeId = typeId;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
        this.backgroundImage = backgroundImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPhaseOne() {
        return phaseOne;
    }

    public void setPhaseOne(int phaseOne) {
        this.phaseOne = phaseOne;
    }

    public int getPhaseTwo() {
        return phaseTwo;
    }

    public void setPhaseTwo(int phaseTwo) {
        this.phaseTwo = phaseTwo;
    }


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Set<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Integer> participants) {
        this.participants = participants;
    }

    public Set<Integer> getJury() {
        return jury;
    }

    public void setJury(Set<Integer> jury) {
        this.jury = jury;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
