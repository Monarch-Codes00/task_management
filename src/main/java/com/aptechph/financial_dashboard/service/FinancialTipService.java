package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.FinancialTipDto;
import com.aptechph.financial_dashboard.entity.FinancialTip;
import com.aptechph.financial_dashboard.repository.FinancialTipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialTipService {

    private final FinancialTipRepository financialTipRepository;

    public List<FinancialTipDto> getAllTips() {
        return financialTipRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public FinancialTipDto getTipById(Long id) {
        FinancialTip tip = financialTipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Financial Tip not found"));
        return mapToDto(tip);
    }

    public List<FinancialTipDto> getTipsByCategory(String category) {
        return financialTipRepository.findByCategory(category).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    // Seed some initial data if empty (Optional, but good for "missing features")
    public void seedTipsIfEmpty() {
        if (financialTipRepository.count() == 0) {
            saveTip("Emergency Fund", "Always maintain 3-6 months of expenses in an easily accessible savings account for emergencies.", "Savings");
            saveTip("50/30/20 Rule", "Allocate 50% of income to needs, 30% to wants, and 20% to savings and debt repayment.", "Budgeting");
            saveTip("Compound Interest", "Start investing early to take advantage of compound interest. Time in the market beats timing the market.", "Investing");
            saveTip("Debt Avalanche", "Pay off high-interest debt first while making minimum payments on others to save money long-term.", "Debt");
        }
    }

    private void saveTip(String title, String content, String category) {
        FinancialTip tip = new FinancialTip();
        tip.setTitle(title);
        tip.setContent(content);
        tip.setCategory(category);
        financialTipRepository.save(tip);
    }

    private FinancialTipDto mapToDto(FinancialTip tip) {
        FinancialTipDto dto = new FinancialTipDto();
        dto.setId(tip.getId());
        dto.setTitle(tip.getTitle());
        dto.setContent(tip.getContent());
        dto.setCategory(tip.getCategory());
        return dto;
    }
}
