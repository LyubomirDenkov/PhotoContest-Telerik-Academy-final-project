package application.photocontest.models;

import javax.persistence.*;

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

}
