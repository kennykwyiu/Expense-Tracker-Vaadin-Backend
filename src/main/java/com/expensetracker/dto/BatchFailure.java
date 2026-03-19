package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a failed expense in a batch operation.
 * Includes the index of the failed item and error message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchFailure {
    private Integer index;
    private String error;
}
