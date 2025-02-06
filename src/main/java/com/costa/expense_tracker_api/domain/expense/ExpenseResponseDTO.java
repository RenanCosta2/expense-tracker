package com.costa.expense_tracker_api.domain.expense;

import java.util.Date;
import java.util.UUID;

public record ExpenseResponseDTO(UUID id, Float value, Date date, String category, String description) {
    public static ExpenseResponseDTO fromEntity(Expense expense){
        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getValue(),
                expense.getDate(),
                expense.getCategory().getCategory(),
                expense.getDescription()
        );
    }
}
