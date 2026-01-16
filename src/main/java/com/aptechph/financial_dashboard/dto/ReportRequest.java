package com.aptechph.financial_dashboard.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class ReportRequest {

    @NotNull
    private YearMonth month;

    @NotNull
    @Positive
    private BigDecimal income;

    @NotNull
    @Positive
    private BigDecimal expenses;
}