package com.costa.expense_tracker_api.repositories;

import com.costa.expense_tracker_api.domain.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
