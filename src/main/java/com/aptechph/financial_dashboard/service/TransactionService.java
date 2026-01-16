package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.TransactionDto;
import com.aptechph.financial_dashboard.dto.TransactionRequest;
import com.aptechph.financial_dashboard.entity.Transaction;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<TransactionDto> getAllTransactions() {
        User currentUser = authService.getCurrentUser();
        return transactionRepository.findByUserOrderByDateDesc(currentUser)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionDto getTransactionById(Long id) {
        User currentUser = authService.getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return convertToDto(transaction);
    }

    @Transactional
    public TransactionDto createTransaction(TransactionRequest request) {
        User currentUser = authService.getCurrentUser();
        Transaction transaction = new Transaction();
        transaction.setName(request.getName());
        transaction.setDate(request.getDate());
        transaction.setAmount(request.getAmount());
        transaction.setType(Transaction.TransactionType.valueOf(request.getType()));
        transaction.setDescription(request.getDescription());
        transaction.setUser(currentUser);

        Transaction saved = transactionRepository.save(transaction);
        return convertToDto(saved);
    }

    @Transactional
    public TransactionDto updateTransaction(Long id, TransactionRequest request) {
        User currentUser = authService.getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setName(request.getName());
        transaction.setDate(request.getDate());
        transaction.setAmount(request.getAmount());
        transaction.setType(Transaction.TransactionType.valueOf(request.getType()));
        transaction.setDescription(request.getDescription());

        Transaction saved = transactionRepository.save(transaction);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        User currentUser = authService.getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalIncome() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = transactionRepository.getTotalIncome(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalExpenses() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = transactionRepository.getTotalExpenses(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = authService.getCurrentUser();
        return transactionRepository.findByUserAndDateRange(currentUser, startDate, endDate)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setName(transaction.getName());
        dto.setDate(transaction.getDate());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType().name());
        dto.setDescription(transaction.getDescription());
        return dto;
    }
}