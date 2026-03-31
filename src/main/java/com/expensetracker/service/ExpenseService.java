package com.expensetracker.service;

import com.expensetracker.dto.*;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ExpenseService encapsulates all business logic for expense operations.
 * Handles validation, authorization, and database interactions.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final Logger logger = Logger.create(ExpenseService.class);

    /**
     * Create a single expense for the authenticated user.
     * Validates input and persists to database.
     */
    public ExpenseResponse createExpense(Integer userId, CreateExpenseRequest request) {
        logger.info("Creating expense", Map.of(
            "userId", userId,
            "date", request.getDate(),
            "amount", request.getAmount(),
            "category", request.getCategory()
        ));

        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Expense expense = new Expense();
            expense.setUser(user);
            expense.setDate(request.getDate());
            expense.setAmount(request.getAmount());
            expense.setCategory(request.getCategory());
            expense.setDescription(request.getDescription());

            Expense saved = expenseRepository.save(expense);

            logger.info("Expense created successfully", Map.of(
                "expenseId", saved.getId(),
                "userId", userId
            ));

            return mapToResponse(saved);
        } catch (Exception e) {
            logger.error("Failed to create expense", e);
            throw new RuntimeException("Failed to create expense: " + e.getMessage());
        }
    }

    /**
     * Update an existing expense.
     * Only the owner can update their expenses.
     * Returns the updated expense or throws if not found/unauthorized.
     */
    public ExpenseResponse updateExpense(Integer userId, Integer expenseId, UpdateExpenseRequest request) {
        logger.info("Updating expense", Map.of(
            "userId", userId,
            "expenseId", expenseId
        ));

        try {
            Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found or unauthorized"));

            if (request.getDate() != null) {
                expense.setDate(request.getDate());
            }
            if (request.getAmount() != null) {
                expense.setAmount(request.getAmount());
            }
            if (request.getCategory() != null) {
                expense.setCategory(request.getCategory());
            }
            if (request.getDescription() != null) {
                expense.setDescription(request.getDescription());
            }

            Expense updated = expenseRepository.save(expense);

            logger.info("Expense updated successfully", Map.of(
                "expenseId", expenseId,
                "userId", userId
            ));

            return mapToResponse(updated);
        } catch (Exception e) {
            logger.error("Failed to update expense", e);
            throw new RuntimeException("Failed to update expense: " + e.getMessage());
        }
    }

    /**
     * Delete an expense.
     * Only the owner can delete their expenses.
     * Returns true if deleted, false if not found/unauthorized.
     */
    public boolean deleteExpense(Integer userId, Integer expenseId) {
        logger.info("Deleting expense", Map.of(
            "userId", userId,
            "expenseId", expenseId
        ));

        try {
            Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found or unauthorized"));

            expenseRepository.delete(expense);

            logger.info("Expense deleted successfully", Map.of(
                "expenseId", expenseId,
                "userId", userId
            ));

            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("Expense not found or unauthorized", Map.of(
                "userId", userId,
                "expenseId", expenseId
            ));
            return false;
        } catch (Exception e) {
            logger.error("Failed to delete expense", e);
            throw new RuntimeException("Failed to delete expense: " + e.getMessage());
        }
    }

    /**
     * List all expenses for a user in a specific month.
     * Returns expenses sorted by date with total amount calculated.
     */
    public ListExpensesResponse listExpensesByMonth(Integer userId, ListExpensesRequest request) {
        logger.info("Listing expenses by month", Map.of(
            "userId", userId,
            "year", request.getYear(),
            "month", request.getMonth()
        ));

        try {
            List<Expense> expenses = expenseRepository.findByUserIdAndYearAndMonth(
                userId,
                request.getYear(),
                request.getMonth()
            );

            BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            List<ExpenseResponse> responses = expenses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

            logger.info("Expenses retrieved successfully", Map.of(
                "userId", userId,
                "count", expenses.size(),
                "total", total
            ));

            ListExpensesResponse response = new ListExpensesResponse();
            response.setExpenses(responses);
            response.setTotal(total);
            response.setCount(expenses.size());
            response.setYear(request.getYear());
            response.setMonth(request.getMonth());

            return response;
        } catch (Exception e) {
            logger.error("Failed to list expenses", e);
            throw new RuntimeException("Failed to list expenses: " + e.getMessage());
        }
    }

    /**
     * Create multiple expenses in a batch operation.
     * Processes up to 10 expenses, returning created records and any failures.
     * Attempts to create each expense individually to maximize success rate.
     */
    public BatchCreateResponse batchCreateExpenses(Integer userId, BatchCreateExpensesRequest request) {
        logger.info("Batch creating expenses", Map.of(
            "userId", userId,
            "count", request.getExpenses().size()
        ));

        List<ExpenseResponse> created = new ArrayList<>();
        List<BatchCreateResponse.BatchFailure> failed = new ArrayList<>();

        for (int i = 0; i < request.getExpenses().size(); i++) {
            try {
                CreateExpenseRequest expenseRequest = request.getExpenses().get(i);
                ExpenseResponse response = createExpense(userId, expenseRequest);
                created.add(response);

                logger.debug("Batch expense created", Map.of(
                    "index", i,
                    "expenseId", response.getId()
                ));
            } catch (Exception e) {
                String errorMessage = e.getMessage() != null ? e.getMessage() : "Unknown error";
                failed.add(new BatchCreateResponse.BatchFailure(i, errorMessage));

                logger.warn("Batch expense creation failed", Map.of(
                    "index", i,
                    "error", errorMessage
                ));
            }
        }

        logger.info("Batch creation completed", Map.of(
            "userId", userId,
            "totalCreated", created.size(),
            "totalFailed", failed.size()
        ));

        BatchCreateResponse response = new BatchCreateResponse();
        response.setCreated(created);
        response.setFailed(failed.isEmpty() ? null : failed);
        response.setTotalCreated(created.size());

        return response;
    }

    /**
     * Convert Expense entity to ExpenseResponse DTO.
     */
    private ExpenseResponse mapToResponse(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setDate(expense.getDate());
        response.setAmount(expense.getAmount());
        response.setCategory(expense.getCategory());
        response.setDescription(expense.getDescription());
        response.setCreatedAt(expense.getCreatedAt());
        response.setUpdatedAt(expense.getUpdatedAt());
        return response;
    }
}
