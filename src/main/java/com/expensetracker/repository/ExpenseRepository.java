package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Expense entity.
 * Provides database access methods for expense operations.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    /**
     * Find all expenses for a user in a specific month.
     * Returns expenses sorted by date in ascending order.
     */
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId " +
           "AND YEAR(e.date) = :year AND MONTH(e.date) = :month " +
           "ORDER BY e.date ASC")
    List<Expense> findByUserIdAndYearAndMonth(
        @Param("userId") Integer userId,
        @Param("year") Integer year,
        @Param("month") Integer month
    );

    /**
     * Find all expenses for a user within a date range.
     * Useful for querying multiple months or custom date ranges.
     */
    List<Expense> findByUserAndDateBetweenOrderByDateAsc(
        User user,
        LocalDate startDate,
        LocalDate endDate
    );

    /**
     * Find a single expense by ID and verify ownership.
     * Returns empty Optional if expense doesn't exist or user doesn't own it.
     */
    Optional<Expense> findByIdAndUserId(Integer id, Integer userId);

}
