package com.aptechph.financial_dashboard.dto;

import lombok.Data;

@Data
public class FinancialTipDto {
    private Long id;
    private String title;
    private String content;
    private String category;
}
