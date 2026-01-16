package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.SubscriptionDto;
import com.aptechph.financial_dashboard.dto.SubscriptionRequest;
import com.aptechph.financial_dashboard.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Subscription management APIs")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @Operation(summary = "Get all subscriptions for current user")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        List<SubscriptionDto> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subscription by ID")
    public ResponseEntity<SubscriptionDto> getSubscriptionById(@PathVariable Long id) {
        SubscriptionDto subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping
    @Operation(summary = "Create a new subscription")
    public ResponseEntity<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        SubscriptionDto subscription = subscriptionService.createSubscription(request);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing subscription")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id,
                                                            @Valid @RequestBody SubscriptionRequest request) {
        SubscriptionDto subscription = subscriptionService.updateSubscription(id, request);
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subscription")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/total")
    @Operation(summary = "Get total active subscription amount")
    public ResponseEntity<BigDecimal> getTotalActiveSubscriptionAmount() {
        BigDecimal total = subscriptionService.getTotalActiveSubscriptionAmount();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/due-range")
    @Operation(summary = "Get subscriptions within due date range")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsByDueDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptionsByDueDateRange(startDate, endDate);
        return ResponseEntity.ok(subscriptions);
    }
}