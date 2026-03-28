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
