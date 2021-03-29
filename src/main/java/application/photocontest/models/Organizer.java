package application.photocontest.models;

import javax.persistence.*;

@Entity
@Table(name = "organizers")
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizer_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_credentials")
    private Credentials credentials;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Organizer() {
    }

    public Organizer(int id, Credentials credentials, String firstName, String lastName) {
        this.id = id;
        this.credentials = credentials;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
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
}
