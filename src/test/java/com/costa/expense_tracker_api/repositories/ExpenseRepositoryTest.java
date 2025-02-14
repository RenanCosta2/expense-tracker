package com.costa.expense_tracker_api.repositories;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ExpenseRepositoryTest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    private User user;
    private Expense expense;
    private Expense expense2;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("loginTest");
        user.setName("nameTest");
        user.setPassword("12345678");
        user.setRole(UserRole.USER);

        this.userRepository.save(user);

        expense = new Expense();
        expense.setValue(100.0F);
        expense.setDate(new Date());
        expense.setCategory(ExpenseCategory.OTHERS);
        expense.setDescription("Description Test");

        expense.setUser(user);

        this.expenseRepository.save(expense);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date date = calendar.getTime();

        expense2 = new Expense();
        expense2.setValue(100.0F);
        expense2.setDate(date);
        expense2.setCategory(ExpenseCategory.OTHERS);
        expense2.setDescription("Description Test");

        expense2.setUser(user);

        this.expenseRepository.save(expense2);

    }

    @Test
    @DisplayName("Should get Expense successfully from DB between custom dates")
    void findExpensesBetweenCustomDate() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Expense> foundedExpense = this.expenseRepository.findExpensesBetweenCustomDate(expense2.getDate(),
                                                                                                new Date(),
                                                                                                user,
                                                                                                pageable);

        assertFalse(foundedExpense.isEmpty());

        assertFalse(foundedExpense.hasNext());
    }

    @Test
    @DisplayName("Should get Expense successfully from DB by ID and User")
    void findByIdAndUserSuccess() {
        Optional<Expense> foundedExpense = this.expenseRepository.findByIdAndUser(expense.getId(), expense.getUser());

        assertTrue(foundedExpense.isPresent());
    }

    @Test
    @DisplayName("Should not get Expense from DB by ID and User")
    void findByIdAndUserFailure() {
        UUID expense_id = UUID.randomUUID();

        Optional<Expense> foundedExpense = this.expenseRepository.findByIdAndUser(expense_id, user);

        assertFalse(foundedExpense.isPresent());
    }
}