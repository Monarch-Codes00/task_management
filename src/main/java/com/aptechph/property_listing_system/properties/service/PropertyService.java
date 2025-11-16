package com.aptechph.property_listing_system.properties.service;

import com.aptechph.property_listing_system.properties.dto.PropertyRequestDto;
import com.aptechph.property_listing_system.properties.dto.PropertyResponseDto;
import com.aptechph.property_listing_system.properties.model.PropertyStatus;

import java.util.List;

public interface PropertyService {
    PropertyResponseDto createProperty(PropertyRequestDto request);

    PropertyResponseDto getPropertyById(Long id);

    List<PropertyResponseDto> getAllProperties();

    PropertyResponseDto updateProperty(Long id, PropertyRequestDto request);

    PropertyResponseDto updatePropertyStatus(Long id, PropertyStatus status);

    void deleteProperty(Long id);
}
