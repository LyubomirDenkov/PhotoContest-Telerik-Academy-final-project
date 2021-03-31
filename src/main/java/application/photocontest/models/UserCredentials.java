package application.photocontest.models;

import application.photocontest.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_credentials"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_images",
            joinColumns = @JoinColumn(name = "user_credentials"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;



    public UserCredentials() {
    }

    public UserCredentials(String userName, String password, Set<Role> roles, Set<Image> images) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.images = images;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        UserCredentials that = (UserCredentials) o;
        return getUserName().equals(that.getUserName()) && getPassword().equals(that.getPassword()) && getRoles().equals(that.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassword(), getRoles());
    }
}
