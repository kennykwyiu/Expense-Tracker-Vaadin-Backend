package com.expensetracker.controller;

import com.expensetracker.dto.MonthlyBalanceResponse;
import com.expensetracker.dto.UpdateMonthlyBalanceRequest;
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

    /**
     * Update expense budget
     * PUT /api/balance/{year}/{month}/budget
     */
    @PutMapping("/{year}/{month}/budget")
    public ResponseEntity<MonthlyBalanceResponse> updateExpenseBudget(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @RequestParam BigDecimal budget
    ) {
        logger.info("PUT /balance/{year}/{month}/budget - Updating budget: " + budget);
        try {
            Integer userId = 1;

            MonthlyBalanceResponse response = balanceService.updateExpenseBudget(userId, year, month, budget);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to update budget - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update last month balance
     * PUT /api/balance/{year}/{month}/last-month-balance
     */
    @PutMapping("/{year}/{month}/last-month-balance")
    public ResponseEntity<MonthlyBalanceResponse> updateLastMonthBalance(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @RequestParam BigDecimal balance
    ) {
        logger.info("PUT /balance/{year}/{month}/last-month-balance - Updating: " + balance);
        try {
            Integer userId = 1;

            MonthlyBalanceResponse response = balanceService.updateLastMonthBalance(userId, year, month, balance);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to update last month balance - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update multiple balance fields
     * PUT /api/balance/{year}/{month}
     */
    @PutMapping("/{year}/{month}")
    public ResponseEntity<MonthlyBalanceResponse> updateMonthlyBalance(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @RequestBody UpdateMonthlyBalanceRequest request
    ) {
        logger.info("PUT /balance/{year}/{month} - Updating balance");
        try {
            Integer userId = 1;

            MonthlyBalanceResponse response = balanceService.updateMonthlyBalance(userId, year, month, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to update monthly balance - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
