package application.photocontest.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CreateImageDto {

    @NotNull
    @Size(max = 300)
    private String url;

    public CreateImageDto() {
    }

    public CreateImageDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
