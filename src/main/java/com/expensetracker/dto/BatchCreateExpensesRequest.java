package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Request DTO for batch creating expenses.
 * Accepts an array of 1-10 expenses for efficient bulk operations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchCreateExpensesRequest {
}
