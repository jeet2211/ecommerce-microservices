package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto dto);
    CategoryDto updateCategory(Long categoryId, CategoryDto dto);
    void deleteCategory(Long id);
    CategoryDto getCategory(Long id);
    List<CategoryDto> getAllCategories();

}
