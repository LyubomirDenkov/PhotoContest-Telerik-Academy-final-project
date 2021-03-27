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

    @Column(name = "rank")
    private String rank;

    @Column(name = "points")
    private int points;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    public User() {
    }

    public User(int id,
                String userName,
                String email,
                String firstName,
                String lastName,
                String password,
                String rank,
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
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

    public String getRank() {
        return rank;
    }

    public void setRank(String role) {
        this.rank = role;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && getPoints() == user.getPoints() && Objects.equals(getUserName(),
                user.getUserName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstName(),
                user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getPassword(),
                user.getPassword()) && Objects.equals(getRank(), user.getRank()) && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getEmail(),
                getFirstName(), getLastName(), getPassword(),
                getRank(), getPoints(), getRoles());
    }
}
