package com.aptechph.financial_dashboard.dto;

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