package com.aptechph.financial_dashboard.repository;

import com.aptechph.financial_dashboard.entity.FinancialTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTipRepository extends JpaRepository<FinancialTip, Long> {
    List<FinancialTip> findByCategory(String category);
}
