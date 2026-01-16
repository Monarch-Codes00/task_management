package com.aptechph.property_listing_system.properties.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "name is required")
    private String name;
}
