package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

/**
 * Request DTO for listing expenses by month.
 * Validates year (1900-2100) and month (1-12).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListExpensesRequest {
}
