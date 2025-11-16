package com.aptechph.property_listing_system.properties.dto;

import com.aptechph.property_listing_system.properties.model.PropertyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PropertyRequestDto {
    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private BigDecimal price;

    @NotBlank(message = "location is required")
    private String location;

    @NotNull(message = "bedrooms is required")
    @Positive(message = "bedrooms must be positive")
    private Integer bedrooms;

    @NotNull(message = "bathrooms is required")
    @Positive(message = "bathrooms must be positive")
    private Integer bathrooms;

    @NotNull(message = "size is required")
    @Positive(message = "size must be positive")
    private Integer size;

    @NotNull(message = "status is required")
    private PropertyStatus status;

    private Long categoryId; // nullable
}
