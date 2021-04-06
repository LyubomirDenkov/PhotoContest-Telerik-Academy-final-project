package application.photocontest.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "points")
public class Points {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "points_id")
    private int id;

    @Column(name = "points")
    private int points;


    public Points() {
    }

    public Points(int id, int points) {
        this.id = id;
        this.points = points;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Points)) return false;
        Points points1 = (Points) o;
        return getId() == points1.getId() && getPoints() == points1.getPoints();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPoints());
    }
}
