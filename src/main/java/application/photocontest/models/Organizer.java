package application.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "organizers")
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizer_id")
    private int id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_credentials")
    private UserCredentials userCredentials;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Organizer() {
    }

    public Organizer(int id, UserCredentials userCredentials, String firstName, String lastName) {
        this.id = id;
        this.userCredentials = userCredentials;
        this.firstName = firstName;
        this.lastName = lastName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organizer)) return false;
        Organizer organizer = (Organizer) o;
        return getId() == organizer.getId() && userCredentials.equals(organizer.userCredentials) &&
                getFirstName().equals(organizer.getFirstName()) && getLastName().equals(organizer.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), userCredentials, getFirstName(), getLastName());
    }
}
