package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private BigDecimal amount;
    private String type;
    private String description;
}

@Data
public class TransactionRequest {

    @NotBlank
    private String name;

    @NotNull
    private LocalDateTime date;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String type;

    @NotBlank
    private String description;
}