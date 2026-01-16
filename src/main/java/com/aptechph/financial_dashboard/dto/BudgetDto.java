package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDto {

    private Long id;
    private String title;
    private String type;
    private BigDecimal amount;
    private BigDecimal spentAmount;
    private String category;
    private BigDecimal remainingAmount;
}

@Data
public class BudgetRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String type;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String category;
}