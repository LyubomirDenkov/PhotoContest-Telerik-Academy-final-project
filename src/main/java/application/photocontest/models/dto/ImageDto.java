package application.photocontest.models.dto;

public class ImageDto {

    private String title;

    private String story;

    public ImageDto() {
    }

    public ImageDto(String title, String story) {
        this.title = title;
        this.story = story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
