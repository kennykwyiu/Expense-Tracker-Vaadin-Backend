package com.expensetracker.util;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import java.util.Map;

/**
 * Logger utility class for structured server-side logging.
 * Provides info, error, debug, and warn levels with consistent formatting.
 *
 * Usage:
 *   Logger logger = new Logger("ExpenseService");
 *   logger.info("Expense created", Map.of("expenseId", 123, "userId", 456));
 *   logger.error("Failed to create expense", exception);
 *   logger.debug("Query result", Map.of("count", 10));
 */
public class Logger {
    private final org.slf4j.Logger logger;
    private final String context;

    public Logger(String context) {
        this.context = context;
        this.logger = LoggerFactory.getLogger(context);
    }

    /**
     * Log informational messages for successful operations.
     * Use for tracking normal application flow and state changes.
     */
    public void info(String message, Map<String, Object> data) {
        if (data != null && !data.isEmpty()) {
            logger.info("{} - {}", message, formatData(data));
        } else {
            logger.info(message);
        }
    }

    public void info(String message) {
        logger.info(message);
    }

    /**
     * Log error messages for exceptions and failures.
     * Automatically extracts stack traces from Exception objects.
     */
    public void error(String message, Exception exception) {
        if (exception != null) {
            logger.error("{} - {}: {}", message, exception.getClass().getSimpleName(), exception.getMessage(), exception);
        } else {
            logger.error(message);
        }
    }

    public void error(String message, Map<String, Object> data) {
        if (data != null && !data.isEmpty()) {
            logger.error("{} - {}", message, formatData(data));
        } else {
            logger.error(message);
        }
    }

    public void error(String message) {
        logger.error(message);
    }

    /**
     * Format data map into a readable string for logging.
     */
    private String formatData(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        data.forEach((key, value) -> {
            sb.append(key).append("=").append(value).append(", ");
        });
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Create a logger instance for a specific context/class.
     * Recommended usage: Logger logger = Logger.create(MyClass.class);
     */
    public static Logger create(Class<?> clazz) {
        return new Logger(clazz.getSimpleName());
    }

    public static Logger create(String context) {
        return new Logger(context);
    }
}
