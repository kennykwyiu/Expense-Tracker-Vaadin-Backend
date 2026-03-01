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
}
