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

    @Column(name = "URL")
    private String url;


    @Column(name = "points")
    private int points;

    public Image() {


    }

    public Image(int id, String url, int points) {
        this.id = id;
        this.url = url;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return getId() == image.getId() && getPoints() == image.getPoints() && getUrl().equals(image.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getPoints());
    }
}
