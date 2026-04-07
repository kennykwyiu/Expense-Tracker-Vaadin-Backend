package com.expensetracker.service;

import com.expensetracker.repository.MonthlyBalanceRepository;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
