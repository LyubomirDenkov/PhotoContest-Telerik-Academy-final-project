package application.photocontest.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UpdateUserDto {

    private static final String PASSWORD_ERROR_MESSAGE = "Password must be at least 4 symbols.";


    @NotEmpty
    @Size(min = 4, message = PASSWORD_ERROR_MESSAGE)
    private String oldPassword;

    @NotEmpty
    @Size(min = 4, message = PASSWORD_ERROR_MESSAGE)
    private String newPassword;

    @NotEmpty
    @Size(min = 4, message = PASSWORD_ERROR_MESSAGE)
    private String repeatPassword;


    public UpdateUserDto() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
