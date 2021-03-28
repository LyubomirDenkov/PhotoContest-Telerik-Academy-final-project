package application.photocontest.models;

import javax.persistence.*;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;


    private byte[] imageByteCode;

    @Column(name = "points")
    private int points;

    @OneToMany
    @JoinTable(name = "images_comments",
    joinColumns = @JoinColumn(name = "image_id"),
    inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments;

    public Image() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
