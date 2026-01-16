package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.CategoryDto;
import com.ecommerce.product_service.event.Category;
import com.ecommerce.product_service.mapper.CategoryMapper;
import com.ecommerce.product_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto dto) {
        Category existingCat = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("id doesnt exist"));
//        existingCat.setId(dto.getId());
        existingCat.setName(dto.getName());
        existingCat.setDescription(dto.getDescription());
        existingCat.setParentId(dto.getParentId());
//        categoryRepository.save(existingCat);
        return categoryMapper.toDto(categoryRepository.save(existingCat));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public CategoryDto getCategory(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto).orElseThrow(()->new RuntimeException("category doesnt existy"));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
