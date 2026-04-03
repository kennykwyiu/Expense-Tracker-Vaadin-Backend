package com.expensetracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MonthlyBalance entity representing user's financial summary for each month.
 * Tracks: last month balance, income, expense budget, and calculated current balance.
 */
@Entity
@Table(name = "monthly_balance", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "year", "month"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBalance {
}
