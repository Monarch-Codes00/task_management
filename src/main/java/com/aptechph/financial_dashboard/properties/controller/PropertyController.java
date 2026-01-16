package com.aptechph.property_listing_system.properties.controller;

import com.aptechph.property_listing_system.properties.dto.PropertyRequestDto;
import com.aptechph.property_listing_system.properties.dto.PropertyResponseDto;
import com.aptechph.property_listing_system.properties.model.PropertyStatus;
import com.aptechph.property_listing_system.properties.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<PropertyResponseDto> createProperty(@Valid @RequestBody PropertyRequestDto request) {
        PropertyResponseDto response = propertyService.createProperty(request);
        return ResponseEntity.created(URI.create("/api/v1/properties/" + response.getId())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getProperty(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(@PathVariable Long id, @Valid @RequestBody PropertyRequestDto request) {
        return ResponseEntity.ok(propertyService.updateProperty(id, request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PropertyResponseDto> updatePropertyStatus(@PathVariable Long id, @RequestBody PropertyStatus status) {
        return ResponseEntity.ok(propertyService.updatePropertyStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
