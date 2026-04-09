package com.expensetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for monthly balance management endpoints.
 * All endpoints are prefixed with /api/balance
 */
@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class BalanceController {
}
