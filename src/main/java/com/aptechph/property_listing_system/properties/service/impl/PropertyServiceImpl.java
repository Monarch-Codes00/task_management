package com.aptechph.property_listing_system.properties.service.impl;

import com.aptechph.property_listing_system.properties.dto.PropertyRequestDto;
import com.aptechph.property_listing_system.properties.dto.PropertyResponseDto;
import com.aptechph.property_listing_system.properties.mapper.PropertyMapper;
import com.aptechph.property_listing_system.properties.model.Category;
import com.aptechph.property_listing_system.properties.model.Property;
import com.aptechph.property_listing_system.properties.model.PropertyStatus;
import com.aptechph.property_listing_system.properties.repository.CategoryRepository;
import com.aptechph.property_listing_system.properties.repository.PropertyRepository;
import com.aptechph.property_listing_system.properties.service.PropertyService;
import com.aptechph.property_listing_system.properties.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final CategoryRepository categoryRepository;
    private final PropertyMapper propertyMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, CategoryRepository categoryRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.categoryRepository = categoryRepository;
        this.propertyMapper = propertyMapper;
    }

    @Override
    public PropertyResponseDto createProperty(PropertyRequestDto request) {
        Property entity = propertyMapper.toEntity(request);
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            entity.setCategory(category);
        }
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Property saved = propertyRepository.save(entity);
        return propertyMapper.toDto(saved);
    }

    @Override
    public PropertyResponseDto getPropertyById(Long id) {
        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        return propertyMapper.toDto(p);
    }

    @Override
    public List<PropertyResponseDto> getAllProperties() {
        return propertyRepository.findAll().stream().map(propertyMapper::toDto).toList();
    }

    @Override
    public PropertyResponseDto updateProperty(Long id, PropertyRequestDto request) {
        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        p.setTitle(request.getTitle());
        p.setDescription(request.getDescription());
        p.setPrice(request.getPrice());
        p.setLocation(request.getLocation());
        p.setBedrooms(request.getBedrooms());
        p.setBathrooms(request.getBathrooms());
        p.setSize(request.getSize());
        p.setStatus(request.getStatus());
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            p.setCategory(category);
        } else {
            p.setCategory(null);
        }
        p.setUpdatedAt(LocalDateTime.now());
        Property updated = propertyRepository.save(p);
        return propertyMapper.toDto(updated);
    }

    @Override
    public PropertyResponseDto updatePropertyStatus(Long id, PropertyStatus status) {
        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        p.setStatus(status);
        p.setUpdatedAt(LocalDateTime.now());
        Property updated = propertyRepository.save(p);
        return propertyMapper.toDto(updated);
    }

    @Override
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Property not found with id: " + id);
        }
        propertyRepository.deleteById(id);
    }
}
