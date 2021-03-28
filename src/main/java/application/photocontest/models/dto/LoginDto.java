package application.photocontest.models.dto;

import javax.validation.constraints.NotNull;

public class LoginDto {

    private static final String USER_NAME_OR_EMAIL_ERROR_MESSAGE = "Username/Email cant be empty";
    private static final String PASSWORD_ERROR_MESSAGE = "Password cant be empty";

    @NotNull(message = USER_NAME_OR_EMAIL_ERROR_MESSAGE)
    private String userNameOrEmail;

    @NotNull(message = PASSWORD_ERROR_MESSAGE)
    private String password;

    public LoginDto() {
    }

    public LoginDto(String userNameOrEmail, String password) {
        this.userNameOrEmail = userNameOrEmail;
        this.password = password;
    }

    public String getUserNameOrEmail() {
        return userNameOrEmail;
    }

    public void setUserNameOrEmail(String userNameOrEmail) {
        this.userNameOrEmail = userNameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
