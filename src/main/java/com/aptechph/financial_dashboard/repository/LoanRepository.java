package com.aptechph.financial_dashboard.repository;

import com.aptechph.financial_dashboard.entity.Loan;
import com.aptechph.financial_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserOrderByDueDateAsc(User user);

    List<Loan> findByUserAndStatus(User user, Loan.LoanStatus status);

    @Query("SELECT SUM(l.totalAmount) FROM Loan l WHERE l.user = :user AND l.status = 'ACTIVE'")
    java.math.BigDecimal getTotalLoanAmount(@Param("user") User user);

    @Query("SELECT SUM(l.paidAmount) FROM Loan l WHERE l.user = :user")
    java.math.BigDecimal getTotalPaidAmount(@Param("user") User user);
}