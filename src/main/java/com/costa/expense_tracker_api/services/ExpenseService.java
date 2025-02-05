package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.expense.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserUtils;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    public Expense createExpense(ExpenseRequestDTO data){

        Expense newExpense = new Expense();
        newExpense.setValue(data.value());
        newExpense.setDate(new Date(data.date()));
        newExpense.setCategory(ExpenseCategory.valueOf(data.category()));
        newExpense.setDescription(data.description());

        User user = (User) UserUtils.getUserLogged();

        newExpense.setUser(user);

        expenseRepository.save(newExpense);

        return newExpense;

    }


}
