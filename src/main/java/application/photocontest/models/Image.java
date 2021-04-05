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


    @Column(name = "image")
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadedBy;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rated_images",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<UserCredentials> jurorAwardedRating;


    public Image() {

    }


    public Image(int id, String title, String story, String url, User uploadedBy, Set<UserCredentials> jurorAwardedRating) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.url = url;
        this.uploadedBy = uploadedBy;
        this.jurorAwardedRating = jurorAwardedRating;
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

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Set<UserCredentials> getJurorAwardedRating() {
        return jurorAwardedRating;
    }

    public void setJurorAwardedRating(Set<UserCredentials> jurorAwardedRating) {
        this.jurorAwardedRating = jurorAwardedRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return getId() == image.getId() && getTitle().equals(image.getTitle()) && getStory().equals(image.getStory()) && getUrl().equals(image.getUrl()) && getUploadedBy().equals(image.getUploadedBy()) && getJurorAwardedRating().equals(image.getJurorAwardedRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getStory(), getUrl(), getUploadedBy(), getJurorAwardedRating());
    }

}
