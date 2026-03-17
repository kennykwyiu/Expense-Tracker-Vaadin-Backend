package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request DTO for updating an expense.
 * All fields are optional to allow partial updates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseRequest {
}
