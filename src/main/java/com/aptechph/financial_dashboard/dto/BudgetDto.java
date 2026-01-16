package com.aptechph.financial_dashboard.dto;

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