package com.costa.expense_tracker_api.repositories;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate AND e.user = :user")
    public Page<Expense> findExpensesBetweenCustomDate(@Param("startDate") Date startDate,
                                              @Param("endDate")Date endDate,
                                              @Param("user") User user,
                                              Pageable pageable);
}
