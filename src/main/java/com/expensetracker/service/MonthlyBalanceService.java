package com.expensetracker.service;

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
}
