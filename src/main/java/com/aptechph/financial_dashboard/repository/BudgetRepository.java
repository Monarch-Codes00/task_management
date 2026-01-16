package com.aptechph.financial_dashboard.repository;

import com.aptechph.financial_dashboard.entity.Budget;
import com.aptechph.financial_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserOrderByCreatedAtDesc(User user);

    List<Budget> findByUserAndCategory(User user, String category);

    @Query("SELECT SUM(b.amount) FROM Budget b WHERE b.user = :user")
    java.math.BigDecimal getTotalBudgetAmount(@Param("user") User user);

    @Query("SELECT SUM(b.spentAmount) FROM Budget b WHERE b.user = :user")
    java.math.BigDecimal getTotalSpentAmount(@Param("user") User user);
}