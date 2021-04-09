package application.photocontest.models;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "image_reviews")
public class ImageReview {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rated_image_id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;


    @Column(name = "points")
    private int points;

    @Column(name = "comment")
    private String comment;



    public ImageReview() {
    }

    public ImageReview(int id, User user, Contest contest, Image image, int points, String comment) {
        this.id = id;
        this.user = user;
        this.contest = contest;
        this.image = image;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
        ImageReview that = (ImageReview) o;
        return getId() == that.getId() && getPoints() == that.getPoints() && getUser().equals(that.getUser()) && getContest().equals(that.getContest()) && getImage().equals(that.getImage()) && getComment().equals(that.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getContest(), getImage(), getPoints(), getComment());
    }
}
