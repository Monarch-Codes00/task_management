package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.LoanDto;
import com.aptechph.financial_dashboard.dto.LoanRequest;
import com.aptechph.financial_dashboard.entity.Loan;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<LoanDto> getAllLoans() {
        User currentUser = authService.getCurrentUser();
        return loanRepository.findByUserOrderByDueDateAsc(currentUser)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LoanDto getLoanById(Long id) {
        User currentUser = authService.getCurrentUser();
        Loan loan = loanRepository.findById(id)
                .filter(l -> l.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return convertToDto(loan);
    }

    @Transactional
    public LoanDto createLoan(LoanRequest request) {
        User currentUser = authService.getCurrentUser();
        Loan loan = new Loan();
        loan.setTitle(request.getTitle());
        loan.setTotalAmount(request.getTotalAmount());
        loan.setMonthlyPayment(request.getMonthlyPayment());
        loan.setDueDate(request.getDueDate());
        loan.setInterestRate(request.getInterestRate());
        loan.setLender(request.getLender());
        loan.setUser(currentUser);

        Loan saved = loanRepository.save(loan);
        return convertToDto(saved);
    }

    @Transactional
    public LoanDto updateLoan(Long id, LoanRequest request) {
        User currentUser = authService.getCurrentUser();
        Loan loan = loanRepository.findById(id)
                .filter(l -> l.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setTitle(request.getTitle());
        loan.setTotalAmount(request.getTotalAmount());
        loan.setMonthlyPayment(request.getMonthlyPayment());
        loan.setDueDate(request.getDueDate());
        loan.setInterestRate(request.getInterestRate());
        loan.setLender(request.getLender());

        Loan saved = loanRepository.save(loan);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteLoan(Long id) {
        User currentUser = authService.getCurrentUser();
        Loan loan = loanRepository.findById(id)
                .filter(l -> l.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loanRepository.delete(loan);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalLoanAmount() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = loanRepository.getTotalLoanAmount(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalPaidAmount() {
        User currentUser = authService.getCurrentUser();
        BigDecimal total = loanRepository.getTotalPaidAmount(currentUser);
        return total != null ? total : BigDecimal.ZERO;
    }

    private LoanDto convertToDto(Loan loan) {
        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setTitle(loan.getTitle());
        dto.setTotalAmount(loan.getTotalAmount());
        dto.setPaidAmount(loan.getPaidAmount());
        dto.setMonthlyPayment(loan.getMonthlyPayment());
        dto.setDueDate(loan.getDueDate());
        dto.setInterestRate(loan.getInterestRate());
        dto.setLender(loan.getLender());
        dto.setStatus(loan.getStatus().name());
        dto.setRemainingAmount(loan.getRemainingAmount());
        return dto;
    }
}