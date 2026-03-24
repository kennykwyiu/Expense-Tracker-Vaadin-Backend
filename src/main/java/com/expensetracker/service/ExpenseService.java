package com.expensetracker.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * ExpenseService encapsulates all business logic for expense operations.
 * Handles validation, authorization, and database interactions.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {
}
