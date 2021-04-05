package application.photocontest.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RateImageDto {

    private static final String POINTS_MUST_BE_POSITIVE = "Points must be positive.";
    private static final String COMMENT_MUST_NOT_BE_NULL = "Comment must no be null.";

    @Positive(message = POINTS_MUST_BE_POSITIVE)
    private int points;

    @NotNull(message = COMMENT_MUST_NOT_BE_NULL)
    private String comment;

    public RateImageDto() {
    }

    public RateImageDto(@Positive int points, @NotNull String comment) {
        this.points = points;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
