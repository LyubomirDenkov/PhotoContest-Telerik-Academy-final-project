package application.photocontest.models;



import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "user_id")
    private int uploaderId;

    @Column(name = "image")
    private String url;

    @Column(name = "points")
    private int points;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rated_images",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<User> jurorAwardedRating;


    public Image() {

    }


    public Image(int id, String title, String story, int uploaderId, String url, int points, Set<User> jurorAwardedRating) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.uploaderId = uploaderId;
        this.url = url;
        this.points = points;
        this.jurorAwardedRating = jurorAwardedRating;
    }

    public int getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(int uploaderId) {
        this.uploaderId = uploaderId;
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

    public Set<User> getJurorAwardedRating() {
        return jurorAwardedRating;
    }

    public void setJurorAwardedRating(Set<User> jurorAwardedRating) {
        this.jurorAwardedRating = jurorAwardedRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return getId() == image.getId() && getUploaderId() == image.getUploaderId() && getPoints() == image.getPoints() && getTitle().equals(image.getTitle()) && getStory().equals(image.getStory()) && getUrl().equals(image.getUrl()) && getJurorAwardedRating().equals(image.getJurorAwardedRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getStory(), getUploaderId(), getUrl(), getPoints(), getJurorAwardedRating());
    }
}
