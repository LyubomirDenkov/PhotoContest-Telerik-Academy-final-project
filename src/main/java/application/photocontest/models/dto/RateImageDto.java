package application.photocontest.models.dto;


import javax.validation.constraints.Positive;

public class RateImageDto {

    @Positive
    private int points;

    public RateImageDto() {
    }

    public RateImageDto(@Positive int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
