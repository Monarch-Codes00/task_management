package com.aptechph.financial_dashboard.repository;

import com.aptechph.financial_dashboard.entity.Savings;
import com.aptechph.financial_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT SUM(s.savingAmount) FROM Savings s WHERE s.user = :user")
    java.math.BigDecimal getTotalSavingsGoal(@Param("user") User user);

    @Query("SELECT SUM(s.currentAmount) FROM Savings s WHERE s.user = :user")
    java.math.BigDecimal getTotalCurrentSavings(@Param("user") User user);
}