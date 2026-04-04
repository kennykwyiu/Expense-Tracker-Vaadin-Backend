package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for monthly balance information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBalanceResponse {
    private Integer id;
    private Integer userId;
    private Integer year;
    private Integer month;
    private BigDecimal lastMonthBalance;
    private BigDecimal incomeThisWeek;
    private BigDecimal expenseBudget;
    private BigDecimal currentBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
