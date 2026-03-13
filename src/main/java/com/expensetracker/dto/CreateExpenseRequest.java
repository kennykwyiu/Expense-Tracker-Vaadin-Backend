package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request DTO for creating a single expense.
 * Validates date format, positive amount, and required fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseRequest {
}
