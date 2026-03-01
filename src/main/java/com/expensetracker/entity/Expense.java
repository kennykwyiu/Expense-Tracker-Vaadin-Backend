package com.expensetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Expense entity representing a daily expense record.
 * Each expense is associated with a user and contains amount, category, and description.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
}
