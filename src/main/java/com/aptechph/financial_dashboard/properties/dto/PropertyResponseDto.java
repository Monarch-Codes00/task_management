package com.aptechph.property_listing_system.properties.dto;

import com.aptechph.property_listing_system.properties.model.PropertyStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PropertyResponseDto {
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String location;

    private Integer bedrooms;

    private Integer bathrooms;

    private Integer size;

    private PropertyStatus status;

    private CategoryResponseDto category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
