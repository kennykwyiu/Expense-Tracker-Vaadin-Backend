package com.expensetracker.controller;

import com.expensetracker.dto.*;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * REST Controller for expense management endpoints.
 * All endpoints are prefixed with /api/expenses (see application.properties).
 * Requires authentication - userId is extracted from the authenticated user context.
 */
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final Logger logger = Logger.create(ExpenseController.class);

    /**
     * Create a single expense.
     * POST /api/expenses
     */
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
        @Valid @RequestBody CreateExpenseRequest request
    ) {
        logger.info("POST /expenses - Creating expense");
        try {
            // TODO: Extract userId from authenticated user context
            // For now, using a hardcoded value for testing
            Integer userId = 1;
            ExpenseResponse response = expenseService.createExpense(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Failed to create expense", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update an existing expense.
     * PUT /api/expenses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
        @PathVariable Integer id,
        @Valid @RequestBody UpdateExpenseRequest request
    ) {
        logger.info("PUT /expenses/{id} - Updating expense", java.util.Map.of("id", id));
        try {
            // TODO: Extract userId from authenticated user context
            Integer userId = 1;
            ExpenseResponse response = expenseService.updateExpense(userId, id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Expense not found", java.util.Map.of("id", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Failed to update expense", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
