package com.aptechph.property_listing_system.properties.mapper;

import com.aptechph.property_listing_system.properties.dto.CategoryRequestDto;
import com.aptechph.property_listing_system.properties.dto.CategoryResponseDto;
import com.aptechph.property_listing_system.properties.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDto dto);
    CategoryResponseDto toDto(Category entity);
}
