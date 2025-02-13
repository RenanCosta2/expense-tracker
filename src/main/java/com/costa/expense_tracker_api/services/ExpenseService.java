package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseResponseDTO;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserUtils;
import com.costa.expense_tracker_api.exceptions.ExpenseNotFound;
import com.costa.expense_tracker_api.exceptions.InvalidUUIDFormatException;
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

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO data){

        Expense newExpense = new Expense();
        newExpense.setValue(data.value());
        newExpense.setDate(new Date(data.date()));
        newExpense.setCategory(ExpenseCategory.valueOf(data.category()));
        newExpense.setDescription(data.description());

        User user = (User) UserUtils.getUserLogged();

        newExpense.setUser(user);

        this.expenseRepository.save(newExpense);

        return ExpenseResponseDTO.fromEntity(newExpense);
    }

    public ExpenseResponseDTO getExpense(String id) {

        UUID expenseUUID;
        try {
            expenseUUID = UUID.fromString(id);

        } catch (IllegalArgumentException exception) {
            throw new InvalidUUIDFormatException();
        }

        User user = (User) UserUtils.getUserLogged();
        Expense expense = this.expenseRepository.findByIdAndUser(expenseUUID, user)
                .orElseThrow((ExpenseNotFound::new));

        return ExpenseResponseDTO.fromEntity(expense);
    }

    public ExpenseResponseDTO updateExpense(String id, ExpenseRequestDTO data){

        UUID expenseUUID;
        try {
            expenseUUID = UUID.fromString(id);

        } catch (IllegalArgumentException exception) {
            throw new InvalidUUIDFormatException();
        }

        User user = (User) UserUtils.getUserLogged();
        Expense expense = this.expenseRepository.findByIdAndUser(expenseUUID, user)
                .orElseThrow((ExpenseNotFound::new));

        if(data.value() != null) expense.setValue(data.value());
        if(data.date() != null) expense.setDate(new Date(data.date()));
        if(data.category() != null) expense.setCategory(ExpenseCategory.valueOf(data.category()));
        if(data.description() != null) expense.setDescription(data.description());

        this.expenseRepository.save(expense);

        return ExpenseResponseDTO.fromEntity(expense);
    }

    public void deleteExpense(String id){

        UUID expenseUUID;
        try {
            expenseUUID = UUID.fromString(id);

        } catch (IllegalArgumentException exception) {
            throw new InvalidUUIDFormatException();
        }

        User user = (User) UserUtils.getUserLogged();
        Expense expense = this.expenseRepository.findByIdAndUser(expenseUUID, user)
                .orElseThrow(ExpenseNotFound::new);

        this.expenseRepository.deleteById(expenseUUID);
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

        return expensePage.map(ExpenseResponseDTO::fromEntity).stream().toList();

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

        return expensePage.map(ExpenseResponseDTO::fromEntity).stream().toList();
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

        return expensePage.map(ExpenseResponseDTO::fromEntity).stream().toList();
    }

    public List<ExpenseResponseDTO> getExpensesBetweenCustomDate(int page, int size, Date startDate, Date endDate){
        Pageable pageable = PageRequest.of(page, size);

        User user = (User) UserUtils.getUserLogged();

        Page<Expense> expensePage = this.expenseRepository.findExpensesBetweenCustomDate(startDate,
                                                                                         endDate,
                                                                                         user,
                                                                                         pageable);

        return expensePage.map(ExpenseResponseDTO::fromEntity).stream().toList();
    }

    public List<ExpenseResponseDTO> getAllExpenses(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        User user = (User) UserUtils.getUserLogged();

        Page<Expense> expensePage = this.expenseRepository.findByUser(user, pageable);

        return expensePage.map(ExpenseResponseDTO::fromEntity).stream().toList();
    }

}
