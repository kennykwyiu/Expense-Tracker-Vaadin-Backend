package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for a single expense.
 * Includes all expense fields with ISO 8601 formatted dates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
}
