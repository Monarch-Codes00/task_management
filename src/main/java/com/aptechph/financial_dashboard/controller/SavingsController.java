package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.SavingsDto;
import com.aptechph.financial_dashboard.dto.SavingsRequest;
import com.aptechph.financial_dashboard.service.SavingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/savings")
@RequiredArgsConstructor
@Tag(name = "Savings", description = "Savings management APIs")
public class SavingsController {

    private final SavingsService savingsService;

    @GetMapping
    @Operation(summary = "Get all savings for current user")
    public ResponseEntity<List<SavingsDto>> getAllSavings() {
        List<SavingsDto> savings = savingsService.getAllSavings();
        return ResponseEntity.ok(savings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get savings by ID")
    public ResponseEntity<SavingsDto> getSavingsById(@PathVariable Long id) {
        SavingsDto savings = savingsService.getSavingsById(id);
        return ResponseEntity.ok(savings);
    }

    @PostMapping
    @Operation(summary = "Create a new savings goal")
    public ResponseEntity<SavingsDto> createSavings(@Valid @RequestBody SavingsRequest request) {
        SavingsDto savings = savingsService.createSavings(request);
        return ResponseEntity.ok(savings);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing savings goal")
    public ResponseEntity<SavingsDto> updateSavings(@PathVariable Long id,
                                                  @Valid @RequestBody SavingsRequest request) {
        SavingsDto savings = savingsService.updateSavings(id, request);
        return ResponseEntity.ok(savings);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a savings goal")
    public ResponseEntity<Void> deleteSavings(@PathVariable Long id) {
        savingsService.deleteSavings(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/goal")
    @Operation(summary = "Get total savings goal amount")
    public ResponseEntity<BigDecimal> getTotalSavingsGoal() {
        BigDecimal total = savingsService.getTotalSavingsGoal();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/summary/current")
    @Operation(summary = "Get total current savings amount")
    public ResponseEntity<BigDecimal> getTotalCurrentSavings() {
        BigDecimal total = savingsService.getTotalCurrentSavings();
        return ResponseEntity.ok(total);
    }
}