package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.BudgetDto;
import com.aptechph.financial_dashboard.dto.BudgetRequest;
import com.aptechph.financial_dashboard.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budgets", description = "Budget management APIs")
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    @Operation(summary = "Get all budgets for current user")
    public ResponseEntity<List<BudgetDto>> getAllBudgets() {
        List<BudgetDto> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get budget by ID")
    public ResponseEntity<BudgetDto> getBudgetById(@PathVariable Long id) {
        BudgetDto budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @PostMapping
    @Operation(summary = "Create a new budget")
    public ResponseEntity<BudgetDto> createBudget(@Valid @RequestBody BudgetRequest request) {
        BudgetDto budget = budgetService.createBudget(request);
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing budget")
    public ResponseEntity<BudgetDto> updateBudget(@PathVariable Long id,
                                                @Valid @RequestBody BudgetRequest request) {
        BudgetDto budget = budgetService.updateBudget(id, request);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a budget")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/total")
    @Operation(summary = "Get total budget amount")
    public ResponseEntity<BigDecimal> getTotalBudgetAmount() {
        BigDecimal total = budgetService.getTotalBudgetAmount();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/summary/spent")
    @Operation(summary = "Get total spent amount")
    public ResponseEntity<BigDecimal> getTotalSpentAmount() {
        BigDecimal total = budgetService.getTotalSpentAmount();
        return ResponseEntity.ok(total);
    }
}