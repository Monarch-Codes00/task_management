package com.aptechph.financial_dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "savings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @Positive
    @Column(name = "saving_amount")
    private BigDecimal savingAmount;

    @NotNull
    @Column(name = "date_taken")
    private LocalDate dateTaken;

    @NotNull
    @Column(name = "amount_left")
    private BigDecimal amountLeft;

    @NotNull
    @Column(name = "current_amount")
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getProgress() {
        if (savingAmount.compareTo(BigDecimal.ZERO) == 0) return 0;
        BigDecimal saved = savingAmount.subtract(amountLeft);
        return saved.divide(savingAmount, 2, BigDecimal.ROUND_HALF_UP)
                   .multiply(BigDecimal.valueOf(100))
                   .intValue();
    }
}