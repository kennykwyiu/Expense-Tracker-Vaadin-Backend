package com.expensetracker.repository;

import com.expensetracker.entity.MonthlyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for MonthlyBalance entity.
 * Provides database operations for monthly balance tracking.
 */
@Repository
public interface MonthlyBalanceRepository extends JpaRepository<MonthlyBalance, Integer> {

    /**
     * Find balance record for specific user, year, and month
     */
    Optional<MonthlyBalance> findByUserIdAndYearAndMonth(Integer userId, Integer year, Integer month);

}
