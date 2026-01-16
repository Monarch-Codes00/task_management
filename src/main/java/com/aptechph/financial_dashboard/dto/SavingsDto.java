package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SavingsDto {

    private Long id;
    private String title;
    private BigDecimal savingAmount;
    private LocalDate dateTaken;
    private BigDecimal amountLeft;
    private BigDecimal currentAmount;
    private LocalDate targetDate;
    private String description;
    private Integer progress;
}

@Data
public class SavingsRequest {

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private BigDecimal savingAmount;

    @NotNull
    private LocalDate dateTaken;

    @NotNull
    private BigDecimal amountLeft;

    private LocalDate targetDate;

    @NotBlank
    private String description;
}