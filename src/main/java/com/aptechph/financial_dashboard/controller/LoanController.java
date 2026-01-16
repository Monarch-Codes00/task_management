package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.LoanDto;
import com.aptechph.financial_dashboard.dto.LoanRequest;
import com.aptechph.financial_dashboard.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Loan management APIs")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    @Operation(summary = "Get all loans for current user")
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        List<LoanDto> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by ID")
    public ResponseEntity<LoanDto> getLoanById(@PathVariable Long id) {
        LoanDto loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    @Operation(summary = "Create a new loan")
    public ResponseEntity<LoanDto> createLoan(@Valid @RequestBody LoanRequest request) {
        LoanDto loan = loanService.createLoan(request);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing loan")
    public ResponseEntity<LoanDto> updateLoan(@PathVariable Long id,
                                            @Valid @RequestBody LoanRequest request) {
        LoanDto loan = loanService.updateLoan(id, request);
        return ResponseEntity.ok(loan);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a loan")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/total")
    @Operation(summary = "Get total loan amount")
    public ResponseEntity<BigDecimal> getTotalLoanAmount() {
        BigDecimal total = loanService.getTotalLoanAmount();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/summary/paid")
    @Operation(summary = "Get total paid amount")
    public ResponseEntity<BigDecimal> getTotalPaidAmount() {
        BigDecimal total = loanService.getTotalPaidAmount();
        return ResponseEntity.ok(total);
    }
}