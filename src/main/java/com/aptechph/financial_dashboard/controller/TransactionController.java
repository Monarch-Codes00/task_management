package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.TransactionDto;
import com.aptechph.financial_dashboard.dto.TransactionRequest;
import com.aptechph.financial_dashboard.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management APIs")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions for current user")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        TransactionDto transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    @Operation(summary = "Create a new transaction")
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionDto transaction = transactionService.createTransaction(request);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing transaction")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id,
                                                          @Valid @RequestBody TransactionRequest request) {
        TransactionDto transaction = transactionService.updateTransaction(id, request);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/income")
    @Operation(summary = "Get total income")
    public ResponseEntity<BigDecimal> getTotalIncome() {
        BigDecimal total = transactionService.getTotalIncome();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/summary/expenses")
    @Operation(summary = "Get total expenses")
    public ResponseEntity<BigDecimal> getTotalExpenses() {
        BigDecimal total = transactionService.getTotalExpenses();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/range")
    @Operation(summary = "Get transactions within date range")
    public ResponseEntity<List<TransactionDto>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDto> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
}