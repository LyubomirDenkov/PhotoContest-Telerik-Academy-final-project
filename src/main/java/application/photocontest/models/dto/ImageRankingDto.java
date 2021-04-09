package application.photocontest.models.dto;

import application.photocontest.models.Image;

public class ImageRankingDto {


    private int image;

    private long points;


    public ImageRankingDto() {
    }

    public ImageRankingDto(int image, long points) {
        this.image = image;
        this.points = points;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
