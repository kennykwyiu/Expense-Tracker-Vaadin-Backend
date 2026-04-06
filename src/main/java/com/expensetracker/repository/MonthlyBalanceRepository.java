package com.expensetracker.repository;

import com.expensetracker.entity.MonthlyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * Find all balance records for user in specific year
     */
    List<MonthlyBalance> findByUserIdAndYearOrderByMonthAsc(Integer userId, Integer year);

    /**
     * Find most recent balance record for user
     */
    @Query("SELECT mb FROM MonthlyBalance mb WHERE mb.userId = :userId ORDER BY mb.year DESC, mb.month DESC LIMIT 1")
    Optional<MonthlyBalance> findLatestByUserId(@Param("userId") Integer userId);



}
