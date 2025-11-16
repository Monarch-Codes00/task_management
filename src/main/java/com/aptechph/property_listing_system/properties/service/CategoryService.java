package com.aptechph.property_listing_system.properties.service;

import com.aptechph.property_listing_system.properties.dto.CategoryRequestDto;
import com.aptechph.property_listing_system.properties.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto request);

    List<CategoryResponseDto> getAllCategories();

    void deleteCategory(Long id);
}
