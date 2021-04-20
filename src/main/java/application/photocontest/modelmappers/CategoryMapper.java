package application.photocontest.modelmappers;

import application.photocontest.models.Category;
import application.photocontest.models.dto.CategoryDto;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryMapper() {
    }

    public Category fromDto(CategoryDto categoryDto) {

        Category category = new Category();

        dtoToObject(categoryDto, category);

        return category;
    }

    public Category dtoToObject(CategoryDto categoryDto, Category category) {
        category.setName(categoryDto.getName());
        return category;
    }
}
