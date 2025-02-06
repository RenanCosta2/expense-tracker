package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.expense.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.ExpenseResponseDTO;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserUtils;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public ExpenseResponseDTO getExpense(UUID id){
        Expense expense = this.expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));

        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getValue(),
                expense.getDate(),
                expense.getCategory().getCategory(),
                expense.getDescription()
        );
    }

    public void deleteExpense(UUID id){
        Expense expense = this.expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));

        this.expenseRepository.deleteById(id);
    }

    public List<ExpenseResponseDTO> getPastWeekExpenses(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dateOneWeekAgo = calendar.getTime();

        User user = (User) UserUtils.getUserLogged();

        Page<Expense> expensePage = this.expenseRepository.findExpensesBetweenCustomDate(dateOneWeekAgo,
                                                                                         new Date(),
                                                                                         user,
                                                                                         pageable);

        return expensePage.map(expense -> new ExpenseResponseDTO(
                expense.getId(),
                expense.getValue(),
                expense.getDate(),
                expense.getCategory().getCategory(),
                expense.getDescription()
        )).stream().toList();

    }

    public List<ExpenseResponseDTO> getPastMonthExpenses(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date dateOneMonthAgo = calendar.getTime();

        User user = (User) UserUtils.getUserLogged();

        Page<Expense> expensePage = this.expenseRepository.findExpensesBetweenCustomDate(dateOneMonthAgo,
                                                                                         new Date(),
                                                                                         user,
                                                                                         pageable);

        return expensePage.map(expense -> new ExpenseResponseDTO(
                expense.getId(),
                expense.getValue(),
                expense.getDate(),
                expense.getCategory().getCategory(),
                expense.getDescription()
        )).stream().toList();
    }

    public List<ExpenseResponseDTO> getPastThreeMonthsExpenses(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date dateThreeMonthsAgo = calendar.getTime();

        User user = (User) UserUtils.getUserLogged();

        Page<Expense> expensePage = this.expenseRepository.findExpensesBetweenCustomDate(dateThreeMonthsAgo,
                                                                                         new Date(),
                                                                                         user,
                                                                                         pageable);

        return expensePage.map(expense -> new ExpenseResponseDTO(
                expense.getId(),
                expense.getValue(),
                expense.getDate(),
                expense.getCategory().getCategory(),
                expense.getDescription()
        )).stream().toList();
    }

    public List<ExpenseResponseDTO> getExpensesBetweenCustomDate(int page, int size, Date startDate, Date endDate){
        Pageable pageable = PageRequest.of(page, size);

        User user = (User) UserUtils.getUserLogged();

        Page<Expense> expensePage = this.expenseRepository.findExpensesBetweenCustomDate(startDate,
                                                                                         endDate,
                                                                                         user,
                                                                                         pageable);

        return expensePage.map(expense -> new ExpenseResponseDTO(
                expense.getId(),
                expense.getValue(),
                expense.getDate(),
                expense.getCategory().getCategory(),
                expense.getDescription()
        )).stream().toList();
    }

}
