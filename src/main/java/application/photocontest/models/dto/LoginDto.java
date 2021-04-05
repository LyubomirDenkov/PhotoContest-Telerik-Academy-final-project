package application.photocontest.models.dto;

import javax.validation.constraints.NotNull;

public class LoginDto {

    private static final String USER_NAME_OR_EMAIL_ERROR_MESSAGE = "Username/Email cant be empty";
    private static final String PASSWORD_ERROR_MESSAGE = "Password cant be empty";

    @NotNull(message = USER_NAME_OR_EMAIL_ERROR_MESSAGE)
    private String userName;

    @NotNull(message = PASSWORD_ERROR_MESSAGE)
    private String password;

    public LoginDto() {
    }

    public LoginDto(String userName, String password) {
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
