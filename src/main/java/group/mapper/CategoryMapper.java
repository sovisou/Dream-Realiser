package group.mapper;

import group.dto.category.CategoryRequestDto;
import group.dto.category.CategoryResponseDto;
import group.dto.event.CreateEventRequestDto;
import group.model.Category;
import group.model.Event;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequestDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        return category;
    }

    public CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public void updateCategoryFromDto(CategoryRequestDto dto, Category category) {
        category.setName(dto.name());
    }
}
