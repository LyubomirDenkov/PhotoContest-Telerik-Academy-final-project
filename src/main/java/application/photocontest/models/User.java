package application.photocontest.models;

import application.photocontest.enums.UserRoles;
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

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @Column(name = "points")
    private int points;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_images",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    public User() {
    }

    public User(int id,
                String userName,
                String email,
                String firstName,
                String lastName,
                String password,
                Rank rank,
                int points,
                Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.rank = rank;
        this.points = points;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public boolean isAdmin() {

        return roles.stream().anyMatch(r -> r.getName().equals(UserRoles.ADMIN.toString()));

    }
    @JsonIgnore
    public boolean isOrganizer() {

        return roles.stream().anyMatch(r -> r.getName().equals(UserRoles.ORGANIZER.toString()));

    }
    @JsonIgnore
    public boolean isUser() {

        return roles.stream().anyMatch(r -> r.getName().equals(UserRoles.USER.toString()));

    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && getPoints() == user.getPoints() && getUserName().equals(user.getUserName()) && getEmail().equals(user.getEmail()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getPassword().equals(user.getPassword()) && getRank().equals(user.getRank()) && getRoles().equals(user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getEmail(), getFirstName(), getLastName(), getPassword(), getRank(), getPoints(), getRoles());
    }
}
