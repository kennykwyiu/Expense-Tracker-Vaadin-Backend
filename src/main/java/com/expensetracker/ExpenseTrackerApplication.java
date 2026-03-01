package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Expense Tracker Backend.
 * Starts the application on port 8080 (configured in application.properties).
 * 
 * To run: mvn spring-boot:run
 * Or: java -jar target/expense-tracker-backend-1.0.0.jar
 */
@SpringBootApplication
public class ExpenseTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }
}
