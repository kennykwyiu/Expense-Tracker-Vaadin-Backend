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
    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Category is required")
    @Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    private String category;

    @Size(max = 500, message = "Description must be 500 characters or less")
    private String description;
}
