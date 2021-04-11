package application.photocontest.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "story")
    private String story;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploader;

    @Column(name = "image")
    private String url;

    @Column(name = "points")
    private int points;


    public Image() {

    }


    public Image(int id, String title, String story, User uploader, String url, int points) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.uploader = uploader;
        this.url = url;
        this.points = points;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return getId() == image.getId() && getTitle().equals(image.getTitle()) && getStory().equals(image.getStory()) && getUploader().equals(image.getUploader()) && getUrl().equals(image.getUrl()) && getPoints() == image.getPoints();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getStory(), getUploader(), getUrl(), getPoints());
    }
}
