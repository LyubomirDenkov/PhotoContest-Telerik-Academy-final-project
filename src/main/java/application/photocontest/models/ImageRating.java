package application.photocontest.models;

import javax.persistence.*;




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

    @Column(name = "comment")
private String comment;




    public ImageRating() {
    }

    public ImageRating(int id, UserCredentials userCredentials, int imageId, int points, String comment) {
        this.id = id;
        this.userCredentials = userCredentials;
        this.imageId = imageId;
        this.points = points;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
