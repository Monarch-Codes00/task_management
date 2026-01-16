package com.aptechph.financial_dashboard.repository;

import com.aptechph.financial_dashboard.entity.Subscription;
import com.aptechph.financial_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserOrderByDueDateAsc(User user);

    List<Subscription> findByUserAndStatus(User user, Subscription.SubscriptionStatus status);

    @Query("SELECT s FROM Subscription s WHERE s.user = :user AND s.dueDate BETWEEN :startDate AND :endDate")
    List<Subscription> findByUserAndDueDateRange(@Param("user") User user,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(s.amount) FROM Subscription s WHERE s.user = :user AND s.status = 'ACTIVE'")
    java.math.BigDecimal getTotalActiveSubscriptionAmount(@Param("user") User user);
}