package com.expensetracker.service;

import com.expensetracker.dto.MonthlyBalanceResponse;
import com.expensetracker.entity.MonthlyBalance;
import com.expensetracker.repository.MonthlyBalanceRepository;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service for managing monthly balance operations.
 * Handles balance tracking, income, and budget management.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MonthlyBalanceService {
    private final MonthlyBalanceRepository balanceRepository;
    private final Logger logger = Logger.create(MonthlyBalanceService.class);

    /**
     * Get monthly balance for user
     */
    public MonthlyBalanceResponse getMonthlyBalance(Integer userId, Integer year, Integer month) {
        logger.info("Getting monthly balance for user " + userId + " - " + year + "-" + month);

        MonthlyBalance balance = balanceRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .orElseGet(() -> createDefaultBalance(userId, year, month));

        return convertToResponse(balance);
    }

    /**
     * Create default balance record if not exists
     */
    private MonthlyBalance createDefaultBalance(Integer userId, Integer year, Integer month) {
        logger.info("Creating default balance record for user " + userId + " - " + year + "-" + month);

        MonthlyBalance balance = new MonthlyBalance();
        balance.setUserId(userId);
        balance.setYear(year);
        balance.setMonth(month);
        balance.setLastMonthBalance(BigDecimal.ZERO);
        balance.setIncomeThisWeek(BigDecimal.ZERO);
        balance.setExpenseBudget(BigDecimal.ZERO);
        balance.setCreatedAt(LocalDateTime.now());
        balance.setUpdatedAt(LocalDateTime.now());

        return balanceRepository.save(balance);
    }

    /**
     * Convert entity to response DTO
     */
    private MonthlyBalanceResponse convertToResponse(MonthlyBalance balance) {
        MonthlyBalanceResponse response = new MonthlyBalanceResponse();
        response.setId(balance.getId());
        response.setUserId(balance.getUserId());
        response.setYear(balance.getYear());
        response.setMonth(balance.getMonth());
        response.setLastMonthBalance(balance.getLastMonthBalance());
        response.setIncomeThisWeek(balance.getIncomeThisWeek());
        response.setExpenseBudget(balance.getExpenseBudget());
        response.setCurrentBalance(balance.getCurrentBalance());
        response.setCreatedAt(balance.getCreatedAt());
        response.setUpdatedAt(balance.getUpdatedAt());
        return response;
    }
}
