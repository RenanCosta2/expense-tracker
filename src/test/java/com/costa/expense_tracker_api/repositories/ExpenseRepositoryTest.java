package com.costa.expense_tracker_api.repositories;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ExpenseRepositoryTest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should get Expense successfully from DB between custom dates")
    void findExpensesBetweenCustomDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date date = calendar.getTime();

        Expense expense = new Expense();
        expense.setValue(100.0F);
        expense.setDate(date);
        expense.setCategory(ExpenseCategory.OTHERS);
        expense.setDescription("Description Test");

        User user = this.createUser();

        expense.setUser(user);

        this.expenseRepository.save(expense);

        Pageable pageable = PageRequest.of(0, 1);

        Page<Expense> foundedExpense = this.expenseRepository.findExpensesBetweenCustomDate(date,
                                                                                                new Date(),
                                                                                                user,
                                                                                                pageable);

        assertFalse(foundedExpense.isEmpty());

        Pageable pageable2 = PageRequest.of(1, 1);

        Page<Expense> foundedExpense2 = this.expenseRepository.findExpensesBetweenCustomDate(date,
                                                                                                new Date(),
                                                                                                user,
                                                                                                pageable2);

        assertTrue(foundedExpense2.isEmpty());
    }

    @Test
    @DisplayName("Should get Expense successfully from DB by ID and User")
    void findByIdAndUser() {
        Expense expense = new Expense();
        expense.setValue(100.0F);
        expense.setDate(new Date());
        expense.setCategory(ExpenseCategory.OTHERS);
        expense.setDescription("Description Test");

        User user = this.createUser();

        expense.setUser(user);

        this.expenseRepository.save(expense);

        Optional<Expense> foundedExpense = this.expenseRepository.findByIdAndUser(expense.getId(), expense.getUser());

        assertTrue(foundedExpense.isPresent());
    }

    private User createUser(){
        User user = new User();
        user.setLogin("loginTest");
        user.setName("nameTest");
        user.setPassword("12345678");
        user.setRole(UserRole.USER);

        this.userRepository.save(user);

        return user;
    }
}