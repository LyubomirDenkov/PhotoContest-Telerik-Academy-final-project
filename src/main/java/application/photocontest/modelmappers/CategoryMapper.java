package application.photocontest.modelmappers;

import application.photocontest.models.Category;
import application.photocontest.models.dto.CategoryDto;
import application.photocontest.repository.contracts.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
