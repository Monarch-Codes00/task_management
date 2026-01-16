package com.aptechph.financial_dashboard.repository;

import com.aptechph.financial_dashboard.entity.Report;
import com.aptechph.financial_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByUserOrderByMonthDesc(User user);

    Optional<Report> findByUserAndMonth(User user, YearMonth month);

    @Query("SELECT r FROM Report r WHERE r.user = :user AND r.month >= :startMonth AND r.month <= :endMonth ORDER BY r.month")
    List<Report> findByUserAndMonthRange(@Param("user") User user,
                                        @Param("startMonth") YearMonth startMonth,
                                        @Param("endMonth") YearMonth endMonth);
}