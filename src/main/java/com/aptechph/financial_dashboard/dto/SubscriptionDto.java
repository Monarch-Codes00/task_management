package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubscriptionDto {

    private Long id;
    private String title;
    private LocalDate dueDate;
    private BigDecimal amount;
    private String status;
    private String category;
    private Boolean autoRenew;
}

@Data
public class SubscriptionRequest {

    @NotBlank
    private String title;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String status;

    @NotBlank
    private String category;

    private Boolean autoRenew = false;
}