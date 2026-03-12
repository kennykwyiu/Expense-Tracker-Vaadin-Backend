package com.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Response DTO for batch create operation.
 * Includes created expenses and any failures with error details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchCreateResponse {

    /**
     * Inner class representing a failed expense in batch operation.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BatchFailure {
    }
}
