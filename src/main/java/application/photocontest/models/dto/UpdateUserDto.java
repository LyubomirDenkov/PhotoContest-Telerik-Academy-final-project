package application.photocontest.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UpdateUserDto {


    @Email
    private String email;

    @NotEmpty
    private String password;


    public UpdateUserDto() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
