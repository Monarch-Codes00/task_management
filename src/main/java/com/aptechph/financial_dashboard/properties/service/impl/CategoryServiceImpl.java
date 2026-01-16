package com.aptechph.property_listing_system.properties.service.impl;

import com.aptechph.property_listing_system.properties.dto.CategoryRequestDto;
import com.aptechph.property_listing_system.properties.dto.CategoryResponseDto;
import com.aptechph.property_listing_system.properties.mapper.CategoryMapper;
import com.aptechph.property_listing_system.properties.model.Category;
import com.aptechph.property_listing_system.properties.repository.CategoryRepository;
import com.aptechph.property_listing_system.properties.service.CategoryService;
import com.aptechph.property_listing_system.properties.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        Category entity = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
