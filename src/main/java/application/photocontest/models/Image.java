package application.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    @Column(name = "imageData")
    private byte[] imageData;

    @Column(name = "points")
    private int points;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "images_comments",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments;

    public Image() {

    }

    public Image(int id, String title, String story, byte[] imageData, int points, Set<Comment> comments) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.imageData = imageData;
        this.points = points;
        this.comments = comments;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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
