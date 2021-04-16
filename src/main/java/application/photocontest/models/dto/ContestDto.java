package application.photocontest.models.dto;


import application.photocontest.models.Image;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;


public class ContestDto {

    public static final String PHASE_ONE_MIN_ERROR_MSG = "Phase one can be minimum 1 day.";

    public static final String PHASE_ONE_MAX_ERROR_MSG = "Phase one can be max 30 days.";

    public static final String PHASE_TWO_MIN_ERROR_MSG = "Phase one can be minimum 1 hour.";
    public static final String PHASE_TWO_MAX_ERROR_MSG = "Phase one can be max 24 hours.";
    private static final String CATEGORY_ID_ERROR_MESSAGE = "Category Id must be positive.";
    private static final String TITLE_ERROR_MESSAGE = "Contest title must be between 5 and 50 symbols.";
    private static final String TYPE_ID_SHOULD_BE_POSITIVE = "Type Id should be positive.";

    @NotNull
    @Size(min = 5, max = 50, message = TITLE_ERROR_MESSAGE)
    private String title;

    @Positive(message = CATEGORY_ID_ERROR_MESSAGE)
    private int categoryId;

    @Positive
    @Min(value = 1, message = PHASE_ONE_MIN_ERROR_MSG)
    @Max(value = 30, message = PHASE_ONE_MAX_ERROR_MSG)
    private int phaseOne;

    @Positive
    @Min(value = 1, message = PHASE_TWO_MIN_ERROR_MSG)
    @Max(value = 24, message = PHASE_TWO_MAX_ERROR_MSG)
    private int phaseTwo;

    @Positive(message = TYPE_ID_SHOULD_BE_POSITIVE)
    private int typeId;

    private Set<Integer> participants;

    private Set<Integer> jury;

    private Set<Image> images;

    public ContestDto() {
    }

    public ContestDto(String title, int categoryId, int phaseOne,
                      int phaseTwo, int typeId, Set<Integer> participants, Set<Integer> jury, Set<Image> images) {
        this.title = title;
        this.categoryId = categoryId;
        this.phaseOne = phaseOne;
        this.phaseTwo = phaseTwo;
        this.typeId = typeId;
        this.participants = participants;
        this.jury = jury;
        this.images = images;
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
