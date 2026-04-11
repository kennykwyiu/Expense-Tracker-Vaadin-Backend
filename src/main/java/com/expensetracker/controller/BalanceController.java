package com.expensetracker.controller;

import com.expensetracker.dto.MonthlyBalanceResponse;
import com.expensetracker.service.MonthlyBalanceService;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
