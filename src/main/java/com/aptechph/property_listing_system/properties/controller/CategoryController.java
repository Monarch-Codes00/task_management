package com.aptechph.property_listing_system.properties.controller;

import com.aptechph.property_listing_system.properties.dto.CategoryRequestDto;
import com.aptechph.property_listing_system.properties.dto.CategoryResponseDto;
import com.aptechph.property_listing_system.properties.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto request) {
        CategoryResponseDto response = categoryService.createCategory(request);
        return ResponseEntity.created(URI.create("/api/v1/categories/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
