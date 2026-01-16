package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanDto {

    private Long id;
    private String title;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal monthlyPayment;
    private LocalDate dueDate;
    private BigDecimal interestRate;
    private String lender;
    private String status;
    private BigDecimal remainingAmount;
}

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