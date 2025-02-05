package com.costa.expense_tracker_api.domain.expense;

import java.util.Date;
import java.util.UUID;

public record ExpenseResponseDTO(UUID id, Float value, Date date, String category, String description) {
}
