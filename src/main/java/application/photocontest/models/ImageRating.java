package application.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "jury_rated_images")
public class ImageRating {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rated_image_id")
    private int id;


    @OneToOne
    @JoinColumn(name = "user_name")
    private UserCredentials userCredentials;


    @Column(name = "image_id")
    private int imageId;


    @Column(name = "points")
    private int points;

    public ImageRating() {
    }

    public ImageRating(int id, UserCredentials userCredentials, int imageId, int points) {
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

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
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
