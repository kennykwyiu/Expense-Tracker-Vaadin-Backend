package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO for updating monthly balance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMonthlyBalanceRequest {
    private BigDecimal lastMonthBalance;
    private BigDecimal incomeThisWeek;
    private BigDecimal expenseBudget;
}
