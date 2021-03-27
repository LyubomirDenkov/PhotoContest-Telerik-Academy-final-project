package application.photocontest.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDto {


    private static final String USER_NAME_ERROR_MESSAGE = "Username must be between 3 and 30 symbols.";
    private static final String FIRST_NAME_ERROR_MESSAGE = "First name must be between 2 and 30 symbols.";
    private static final String LAST_NAME_ERROR_MESSAGE = "Last name must be between 2 and 30 symbols.";


    @NotNull
    @Size(min = 3, max = 30, message = USER_NAME_ERROR_MESSAGE)
    private String userName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String repeatPassword;

    @NotNull
    @Size(min = 2, max = 20, message = FIRST_NAME_ERROR_MESSAGE)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20, message = LAST_NAME_ERROR_MESSAGE)
    private String lastName;

    public RegisterDto() {
    }

    public RegisterDto(String userName,
                       String email,
                       String password,
                       String repeatPassword,
                       String firstName,
                       String lastName) {

        this.userName = userName;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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
}
