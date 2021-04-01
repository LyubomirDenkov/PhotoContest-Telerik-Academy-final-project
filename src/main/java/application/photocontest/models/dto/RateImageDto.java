package application.photocontest.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RateImageDto {

    @Positive
    private int points;

    @NotNull
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
