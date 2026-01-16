package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.*;
import com.aptechph.financial_dashboard.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Dashboard summary APIs")
public class DashboardController {

    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final SubscriptionService subscriptionService;
    private final SavingsService savingsService;
    private final LoanService loanService;
    private final ReportService reportService;

    @GetMapping("/summary")
    @Operation(summary = "Get comprehensive dashboard summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();

        // Financial overview
        BigDecimal totalIncome = transactionService.getTotalIncome();
        BigDecimal totalExpenses = transactionService.getTotalExpenses();
        BigDecimal netIncome = totalIncome.subtract(totalExpenses);

        summary.put("totalIncome", totalIncome);
        summary.put("totalExpenses", totalExpenses);
        summary.put("netIncome", netIncome);

        // Budget summary
        BigDecimal totalBudget = budgetService.getTotalBudgetAmount();
        BigDecimal totalSpent = budgetService.getTotalSpentAmount();
        BigDecimal remainingBudget = totalBudget.subtract(totalSpent);

        summary.put("totalBudget", totalBudget);
        summary.put("totalSpent", totalSpent);
        summary.put("remainingBudget", remainingBudget);

        // Subscription summary
        BigDecimal totalSubscriptions = subscriptionService.getTotalActiveSubscriptionAmount();
        summary.put("totalSubscriptions", totalSubscriptions);

        // Savings summary
        BigDecimal totalSavingsGoal = savingsService.getTotalSavingsGoal();
        BigDecimal totalCurrentSavings = savingsService.getTotalCurrentSavings();
        summary.put("totalSavingsGoal", totalSavingsGoal);
        summary.put("totalCurrentSavings", totalCurrentSavings);

        // Loan summary
        BigDecimal totalLoanAmount = loanService.getTotalLoanAmount();
        BigDecimal totalPaidAmount = loanService.getTotalPaidAmount();
        BigDecimal remainingLoanAmount = totalLoanAmount.subtract(totalPaidAmount);
        summary.put("totalLoanAmount", totalLoanAmount);
        summary.put("remainingLoanAmount", remainingLoanAmount);

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/recent-transactions")
    @Operation(summary = "Get recent transactions for dashboard")
    public ResponseEntity<List<TransactionDto>> getRecentTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        // Return only first 5 for dashboard
        List<TransactionDto> recent = transactions.size() > 5 ?
            transactions.subList(0, 5) : transactions;
        return ResponseEntity.ok(recent);
    }

    @GetMapping("/budgets-overview")
    @Operation(summary = "Get budgets overview for dashboard")
    public ResponseEntity<List<BudgetDto>> getBudgetsOverview() {
        List<BudgetDto> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/upcoming-subscriptions")
    @Operation(summary = "Get upcoming subscriptions for dashboard")
    public ResponseEntity<List<SubscriptionDto>> getUpcomingSubscriptions() {
        List<SubscriptionDto> subscriptions = subscriptionService.getAllSubscriptions();
        // Filter active subscriptions and return first 5
        List<SubscriptionDto> upcoming = subscriptions.stream()
            .filter(s -> "ACTIVE".equals(s.getStatus()))
            .limit(5)
            .toList();
        return ResponseEntity.ok(upcoming);
    }

    @GetMapping("/savings-progress")
    @Operation(summary = "Get savings progress for dashboard")
    public ResponseEntity<List<SavingsDto>> getSavingsProgress() {
        List<SavingsDto> savings = savingsService.getAllSavings();
        return ResponseEntity.ok(savings);
    }

    @GetMapping("/loans-overview")
    @Operation(summary = "Get loans overview for dashboard")
    public ResponseEntity<List<LoanDto>> getLoansOverview() {
        List<LoanDto> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/monthly-reports")
    @Operation(summary = "Get monthly reports for dashboard")
    public ResponseEntity<List<ReportDto>> getMonthlyReports() {
        List<ReportDto> reports = reportService.getAllReports();
        // Return last 6 months
        List<ReportDto> recent = reports.size() > 6 ?
            reports.subList(0, 6) : reports;
        return ResponseEntity.ok(recent);
    }
}