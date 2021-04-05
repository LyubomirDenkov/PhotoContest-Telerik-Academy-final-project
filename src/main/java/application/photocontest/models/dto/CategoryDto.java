package application.photocontest.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryDto {

    private static final String CATEGORY_ERROR_MESSAGE = "Category name must be between 3 and 30 symbols.";


    @NotNull
    @Size(min = 3, max = 30, message = CATEGORY_ERROR_MESSAGE)
    private String name;


    public CategoryDto() {
    }

    public CategoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
