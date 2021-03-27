package application.photocontest.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CreateImageDto {

    public static final String USER_ERROR_MESSAGE = "User Id must be positive.";
    @NotNull
    @Size(max = 300)
    private String url;

    @Positive(message = USER_ERROR_MESSAGE)
    private int userId;

    public CreateImageDto() {
    }

    public CreateImageDto(String url, int userId) {
        this.url = url;
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
