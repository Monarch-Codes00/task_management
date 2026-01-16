package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanRequest {

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private BigDecimal totalAmount;

    @NotNull
    @Positive
    private BigDecimal monthlyPayment;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Positive
    private BigDecimal interestRate;

    @NotBlank
    private String lender;
}