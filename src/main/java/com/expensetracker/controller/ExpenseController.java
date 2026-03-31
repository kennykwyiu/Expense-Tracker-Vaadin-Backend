package com.expensetracker.controller;

import com.expensetracker.dto.*;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.util.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * REST Controller for expense management endpoints.
 * All endpoints are prefixed with /api/expenses (see application.properties).
 * Requires authentication - userId is extracted from the authenticated user context.
 */
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

}
