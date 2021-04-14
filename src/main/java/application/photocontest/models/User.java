package application.photocontest.models;

import application.photocontest.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("ALL")
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

    @Column(name = "profileImage")
    private String profileImage;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_images",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_points",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "points_id"))
    private Set<Points> points;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_notifications",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    private Set<Notification> notifications;

    public User() {
    }

    public User(int id, UserCredentials userCredentials, String firstName, String lastName, String profileImage, Set<Role> roles, Set<Image> images, Set<Points> points) {
        this.id = id;
        this.userCredentials = userCredentials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.roles = roles;
        this.images = images;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Points> getPoints() {
        return points;
    }

    public void setPoints(Set<Points> points) {
        this.points = points;
    }


    public Set<Notification> getMessages() {
        return notifications;
    }

    public void setMessages(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    @JsonIgnore
    public boolean isOrganizer() {

        return roles.stream().anyMatch(r -> r.getName().equals(UserRoles.ORGANIZER.toString()));

    }

    @JsonIgnore
    public boolean isUser() {

        return roles.stream().anyMatch(r -> r.getName().equals(UserRoles.USER.toString()));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && getUserCredentials().equals(user.getUserCredentials()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getProfileImage().equals(user.getProfileImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserCredentials(), getFirstName(), getLastName(), getProfileImage());
    }
}
