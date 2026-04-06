package com.expensetracker.repository;

import com.expensetracker.entity.MonthlyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for MonthlyBalance entity.
 * Provides database operations for monthly balance tracking.
 */
@Repository
public interface MonthlyBalanceRepository extends JpaRepository<MonthlyBalance, Integer> {
}
