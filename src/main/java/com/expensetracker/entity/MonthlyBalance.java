package com.expensetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MonthlyBalance entity representing user's financial summary for each month.
 * Tracks: last month balance, income, expense budget, and calculated current balance.
 */
@Entity
@Table(name = "monthly_balance", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "year", "month"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "last_month_balance", precision = 10, scale = 2)
    private BigDecimal lastMonthBalance;

    @Column(name = "income_this_week", precision = 10, scale = 2)
    private BigDecimal incomeThisWeek;

    @Column(name = "expense_budget", precision = 10, scale = 2)
    private BigDecimal expenseBudget;

    @Column(name = "current_balance", precision = 10, scale = 2, insertable = false, updatable = false)
    private BigDecimal currentBalance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
