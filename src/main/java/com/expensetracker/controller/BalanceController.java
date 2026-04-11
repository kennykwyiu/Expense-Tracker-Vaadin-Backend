package com.expensetracker.controller;

import com.expensetracker.dto.MonthlyBalanceResponse;
import com.expensetracker.service.MonthlyBalanceService;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * REST Controller for monthly balance management endpoints.
 * All endpoints are prefixed with /api/balance
 */
@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class BalanceController {
    private final MonthlyBalanceService balanceService;
    private final Logger logger = Logger.create(BalanceController.class);

    /**
     * Get monthly balance for user
     * GET /api/balance/{year}/{month}
     */
    @GetMapping("/{year}/{month}")
    public ResponseEntity<MonthlyBalanceResponse> getMonthlyBalance(
            @PathVariable Integer year,
            @PathVariable Integer month
    ) {
        logger.info("GET /balance - Getting balance for " + year + "-" + month);
        try {
            // TODO: Extract userId from authenticated user context
            // For now, using hardcoded userId = 1
            Integer userId = 1;

            MonthlyBalanceResponse response = balanceService.getMonthlyBalance(userId, year, month);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to get monthly balance - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create monthly balance record
     * POST /api/balance
     */
    @PostMapping
    public ResponseEntity<MonthlyBalanceResponse> createMonthlyBalance(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam(required = false) BigDecimal lastMonthBalance,
            @RequestParam(required = false) BigDecimal expenseBudget
    ) {
        logger.info("POST /balance - Creating balance for " + year + "-" + month);
        try {
            // TODO: Extract userId from authenticated user context
            Integer userId = 1;

            MonthlyBalanceResponse response = balanceService.createMonthlyBalance(
                    userId, year, month, lastMonthBalance, expenseBudget
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Failed to create monthly balance - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update income for the week
     * PUT /api/balance/{year}/{month}/income
     */
    @PutMapping("/{year}/{month}/income")
    public ResponseEntity<MonthlyBalanceResponse> updateIncomeThisWeek(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @RequestParam BigDecimal income
    ) {
        logger.info("PUT /balance/{year}/{month}/income - Updating income: " + income);
        try {
            Integer userId = 1;

            MonthlyBalanceResponse response = balanceService.updateIncomeThisWeek(userId, year, month, income);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to update income - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
