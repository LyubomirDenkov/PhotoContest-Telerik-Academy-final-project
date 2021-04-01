package application.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


    @JsonIgnore
    @Column(name = "image")
    private String image;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "images_comments",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jury_rated_images",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<UserCredentials> jurorAwardedRating;


    public Image() {

    }

    public Image(int id, String title, String story, String image,
                 Set<Comment> comments, Set<UserCredentials> jurorAwardedRating) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.image = image;
        this.comments = comments;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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
        if (!(o instanceof Image)) return false;
        Image image1 = (Image) o;
        return getId() == image1.getId() && getTitle().equals(image1.getTitle()) && getStory().equals(image1.getStory()) && getImage().equals(image1.getImage()) && getComments().equals(image1.getComments()) && getJurorAwardedRating().equals(image1.getJurorAwardedRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getStory(), getImage(), getComments(), getJurorAwardedRating());
    }
}
