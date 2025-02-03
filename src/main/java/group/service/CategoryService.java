package group.service;

import group.dto.category.CategoryRequestDto;
import group.dto.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto dto);

    CategoryResponseDto updateCategory(String id, CategoryRequestDto dto);

    void deleteCategoryById(String id);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategoryById(String id);
}
