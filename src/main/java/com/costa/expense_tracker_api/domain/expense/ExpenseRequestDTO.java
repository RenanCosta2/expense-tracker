package com.costa.expense_tracker_api.domain.expense;

public record ExpenseRequestDTO(Float value, Long date, String category, String description) {
}
