package com.aptechph.property_listing_system.properties.mapper;

import com.aptechph.property_listing_system.properties.dto.PropertyRequestDto;
import com.aptechph.property_listing_system.properties.dto.PropertyResponseDto;
import com.aptechph.property_listing_system.properties.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface PropertyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Property toEntity(PropertyRequestDto dto);

    PropertyResponseDto toDto(Property entity);
}
