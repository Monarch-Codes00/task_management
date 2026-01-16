package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.SubscriptionDto;
import com.aptechph.financial_dashboard.dto.SubscriptionRequest;
import com.aptechph.financial_dashboard.entity.Subscription;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<SubscriptionDto> getAllSubscriptions() {
        User currentUser = authService.getCurrentUser();
        return subscriptionRepository.findByUserOrderByDueDateAsc(currentUser)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriptionDto getSubscriptionById(Long id) {
        User currentUser = authService.getCurrentUser();
        Subscription subscription = subscriptionRepository.findById(id)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        return convertToDto(subscription);
    }

    @Transactional
    public SubscriptionDto createSubscription(SubscriptionRequest request) {
        User currentUser = authService.getCurrentUser();
        Subscription subscription = new Subscription();
        subscription.setTitle(request.getTitle());
        subscription.setDueDate(request.getDueDate());
        subscription.setAmount(request.getAmount());
        subscription.setStatus(Subscription.SubscriptionStatus.valueOf(request.getStatus()));
        subscription.setCategory(request.getCategory());
        subscription.setAutoRenew(request.getAutoRenew());
        subscription.setUser(currentUser);

        Subscription saved = subscriptionRepository.save(subscription);
        return convertToDto(saved);
    }

    @Transactional
    public SubscriptionDto updateSubscription(Long id, SubscriptionRequest request) {
        User currentUser = authService.getCurrentUser();
        Subscription subscription = subscriptionRepository.findById(id)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setTitle(request.getTitle());
        subscription.setDueDate(request.getDueDate());
        subscription.setAmount(request.getAmount());
        subscription.setStatus(Subscription.SubscriptionStatus.valueOf(request.getStatus()));
        subscription.setCategory(request.getCategory());
        subscription.setAutoRenew(request.getAutoRenew());

        Subscription saved = subscriptionRepository.save(subscription);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteSubscription(Long id) {
        User currentUser = authService.getCurrentUser();
        Subscription subscription = subscriptionRepository.findById(id)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscriptionRepository.delete(subscription);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalActiveSubscriptionAmount() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = subscriptionRepository.getTotalActiveSubscriptionAmount(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDto> getSubscriptionsByDueDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = authService.getCurrentUser();
        return subscriptionRepository.findByUserAndDueDateRange(currentUser, startDate, endDate)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SubscriptionDto convertToDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setTitle(subscription.getTitle());
        dto.setDueDate(subscription.getDueDate());
        dto.setAmount(subscription.getAmount());
        dto.setStatus(subscription.getStatus().name());
        dto.setCategory(subscription.getCategory());
        dto.setAutoRenew(subscription.getAutoRenew());
        return dto;
    }
}