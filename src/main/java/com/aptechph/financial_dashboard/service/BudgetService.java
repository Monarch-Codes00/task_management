package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.BudgetDto;
import com.aptechph.financial_dashboard.dto.BudgetRequest;
import com.aptechph.financial_dashboard.entity.Budget;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<BudgetDto> getAllBudgets() {
        User currentUser = authService.getCurrentUser();
        return budgetRepository.findByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BudgetDto getBudgetById(Long id) {
        User currentUser = authService.getCurrentUser();
        Budget budget = budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        return convertToDto(budget);
    }

    @Transactional
    public BudgetDto createBudget(BudgetRequest request) {
        User currentUser = authService.getCurrentUser();
        Budget budget = new Budget();
        budget.setTitle(request.getTitle());
        budget.setType(Budget.BudgetType.valueOf(request.getType()));
        budget.setAmount(request.getAmount());
        budget.setCategory(request.getCategory());
        budget.setUser(currentUser);

        Budget saved = budgetRepository.save(budget);
        return convertToDto(saved);
    }

    @Transactional
    public BudgetDto updateBudget(Long id, BudgetRequest request) {
        User currentUser = authService.getCurrentUser();
        Budget budget = budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budget.setTitle(request.getTitle());
        budget.setType(Budget.BudgetType.valueOf(request.getType()));
        budget.setAmount(request.getAmount());
        budget.setCategory(request.getCategory());

        Budget saved = budgetRepository.save(budget);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteBudget(Long id) {
        User currentUser = authService.getCurrentUser();
        Budget budget = budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budgetRepository.delete(budget);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalBudgetAmount() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = budgetRepository.getTotalBudgetAmount(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalSpentAmount() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = budgetRepository.getTotalSpentAmount(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    private BudgetDto convertToDto(Budget budget) {
        BudgetDto dto = new BudgetDto();
        dto.setId(budget.getId());
        dto.setTitle(budget.getTitle());
        dto.setType(budget.getType().name());
        dto.setAmount(budget.getAmount());
        dto.setSpentAmount(budget.getSpentAmount());
        dto.setCategory(budget.getCategory());
        dto.setRemainingAmount(budget.getAmount().subtract(budget.getSpentAmount()));
        return dto;
    }
}