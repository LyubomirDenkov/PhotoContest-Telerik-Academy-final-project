package application.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_credentials", referencedColumnName = "user_name")
    private UserCredentials userCredentials;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @Column(name = "points")
    private int points;


    public User() {
    }

    public User(int id, UserCredentials userCredentials, String firstName, String lastName, Rank rank, int points) {
        this.id = id;
        this.userCredentials = userCredentials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
        this.points = points;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public UserCredentials getCredentials() {
        return userCredentials;
    }

    public void setCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
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
        User user = (User) o;
        return getId() == user.getId() && getPoints() == user.getPoints() && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getRank().equals(user.getRank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getRank(), getPoints());
    }
}
