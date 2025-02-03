package group.service.impl;

import group.dto.category.CategoryRequestDto;
import group.dto.category.CategoryResponseDto;
import group.exception.EntityNotFoundException;
import group.mapper.CategoryMapper;
import group.model.Category;
import group.repository.CategoryRepository;
import group.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        Category category = categoryMapper.toEntity(dto);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(String id, CategoryRequestDto dto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
        categoryMapper.updateCategoryFromDto(dto, existingCategory);
        existingCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDto(existingCategory);
    }

    @Override
    public void deleteCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
        return categoryMapper.toDto(category);
    }

    public Category getCategoryByName(String name) {
        Category category = categoryRepository
                .findAll()
                .stream()
                .filter(category1 -> category1.getName().contains(name))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return category;
    }
}
