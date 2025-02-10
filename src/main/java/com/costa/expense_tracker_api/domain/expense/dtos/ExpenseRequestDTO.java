package com.costa.expense_tracker_api.domain.expense.dtos;

import jakarta.validation.constraints.NotNull;

public record ExpenseRequestDTO(
        @NotNull(message = "Expense value is required.") Float value,
        @NotNull(message = "Expense date is required.") Long date,
        @NotNull(message = "Expense category is required.") String category,
        String description) {
}
