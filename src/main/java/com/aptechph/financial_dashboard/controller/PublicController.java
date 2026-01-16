package com.aptechph.financial_dashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "Public", description = "Public APIs that don't require authentication")
public class PublicController {

    @GetMapping("/financial-tips")
    @Operation(summary = "Get financial advice tips")
    public ResponseEntity<List<Map<String, String>>> getFinancialTips() {
        List<Map<String, String>> tips = Arrays.asList(
            Map.of(
                "id", "1",
                "title", "Emergency Fund",
                "description", "Always maintain 3-6 months of expenses in an easily accessible savings account for emergencies.",
                "category", "Savings"
            ),
            Map.of(
                "id", "2",
                "title", "50/30/20 Rule",
                "description", "Allocate 50% of income to needs, 30% to wants, and 20% to savings and debt repayment.",
                "category", "Budgeting"
            ),
            Map.of(
                "id", "3",
                "title", "Compound Interest",
                "description", "Start investing early to take advantage of compound interest. Time in the market beats timing the market.",
                "category", "Investing"
            ),
            Map.of(
                "id", "4",
                "title", "Debt Avalanche",
                "description", "Pay off high-interest debt first while making minimum payments on others to save money long-term.",
                "category", "Debt"
            ),
            Map.of(
                "id", "5",
                "title", "Track Your Spending",
                "description", "Monitor your expenses regularly to identify areas where you can cut back and save more.",
                "category", "Budgeting"
            ),
            Map.of(
                "id", "6",
                "title", "Diversify Investments",
                "description", "Don't put all your eggs in one basket. Spread your investments across different asset classes.",
                "category", "Investing"
            )
        );

        return ResponseEntity.ok(tips);
    }
}