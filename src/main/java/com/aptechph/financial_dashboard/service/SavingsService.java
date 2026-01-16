package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.SavingsDto;
import com.aptechph.financial_dashboard.dto.SavingsRequest;
import com.aptechph.financial_dashboard.entity.Savings;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.SavingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingsService {

    private final SavingsRepository savingsRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<SavingsDto> getAllSavings() {
        User currentUser = authService.getCurrentUser();
        return savingsRepository.findByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SavingsDto getSavingsById(Long id) {
        User currentUser = authService.getCurrentUser();
        Savings savings = savingsRepository.findById(id)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Savings not found"));
        return convertToDto(savings);
    }

    @Transactional
    public SavingsDto createSavings(SavingsRequest request) {
        User currentUser = authService.getCurrentUser();
        Savings savings = new Savings();
        savings.setTitle(request.getTitle());
        savings.setSavingAmount(request.getSavingAmount());
        savings.setDateTaken(request.getDateTaken());
        savings.setAmountLeft(request.getAmountLeft());
        savings.setTargetDate(request.getTargetDate());
        savings.setDescription(request.getDescription());
        savings.setUser(currentUser);

        Savings saved = savingsRepository.save(savings);
        return convertToDto(saved);
    }

    @Transactional
    public SavingsDto updateSavings(Long id, SavingsRequest request) {
        User currentUser = authService.getCurrentUser();
        Savings savings = savingsRepository.findById(id)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Savings not found"));

        savings.setTitle(request.getTitle());
        savings.setSavingAmount(request.getSavingAmount());
        savings.setDateTaken(request.getDateTaken());
        savings.setAmountLeft(request.getAmountLeft());
        savings.setTargetDate(request.getTargetDate());
        savings.setDescription(request.getDescription());

        Savings saved = savingsRepository.save(savings);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteSavings(Long id) {
        User currentUser = authService.getCurrentUser();
        Savings savings = savingsRepository.findById(id)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Savings not found"));
        savingsRepository.delete(savings);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalSavingsGoal() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = savingsRepository.getTotalSavingsGoal(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalCurrentSavings() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = savingsRepository.getTotalCurrentSavings(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    private SavingsDto convertToDto(Savings savings) {
        SavingsDto dto = new SavingsDto();
        dto.setId(savings.getId());
        dto.setTitle(savings.getTitle());
        dto.setSavingAmount(savings.getSavingAmount());
        dto.setDateTaken(savings.getDateTaken());
        dto.setAmountLeft(savings.getAmountLeft());
        dto.setCurrentAmount(savings.getCurrentAmount());
        dto.setTargetDate(savings.getTargetDate());
        dto.setDescription(savings.getDescription());
        dto.setProgress(savings.getProgress());
        return dto;
    }
}