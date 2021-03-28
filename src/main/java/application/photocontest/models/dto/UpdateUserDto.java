package application.photocontest.models.dto;

import javax.validation.constraints.Email;
import java.util.Optional;

public class UpdateUserDto {


    @Email
    private String email;

    private Optional<String> oldPassword;

    private Optional<String> newPassword;

    private Optional<String> repeatPassword;

    public UpdateUserDto() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<String> getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(Optional<String> oldPassword) {
        this.oldPassword = oldPassword;
    }

    public Optional<String> getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(Optional<String> newPassword) {
        this.newPassword = newPassword;
    }

    public Optional<String> getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(Optional<String> repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
