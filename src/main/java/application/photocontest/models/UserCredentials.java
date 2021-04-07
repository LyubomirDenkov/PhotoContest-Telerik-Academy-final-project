package application.photocontest.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;


    public UserCredentials() {
    }

    public UserCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCredentials)) return false;
        UserCredentials that = (UserCredentials) o;
        return getUserName().equals(that.getUserName()) && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassword());
    }
}
