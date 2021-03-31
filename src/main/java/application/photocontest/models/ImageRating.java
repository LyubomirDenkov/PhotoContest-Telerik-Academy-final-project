package application.photocontest.models;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Set;


@Entity
@Table(name = "jury_rated_images")
public class ImageRating {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageRating_id")
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jury_rated_images",
            joinColumns = @JoinColumn(name = "user_credentials"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<UserCredentials> userCredentials;

    @Positive
    @Column(name = "image_id")
    private int imageId;

    @Positive
    @Column(name = "points")
    private int points;

    public ImageRating() {
    }

    public ImageRating(int id, Set<UserCredentials> userCredentials, int imageId, int points) {
        this.id = id;
        this.userCredentials = userCredentials;
        this.imageId = imageId;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<UserCredentials> getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(Set<UserCredentials> userName) {
        this.userCredentials = userName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
