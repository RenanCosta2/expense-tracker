package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseResponseDTO;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserRole;
import com.costa.expense_tracker_api.domain.user.UserUtils;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    ExpenseRepository expenseRepository;

    @Autowired
    @InjectMocks
    ExpenseService expenseService;

    private MockedStatic<UserUtils> mockStatic;

    private User user;

    private Expense expense;

    @BeforeEach
    void setUp() {

        mockStatic = mockStatic(UserUtils.class);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin("loginTest");
        user.setName("nameTest");
        user.setPassword("12345678");
        user.setRole(UserRole.USER);

        expense = new Expense();
        expense.setId(UUID.randomUUID());
        expense.setValue(100.00F);
        expense.setDate(new Date());
        expense.setCategory(ExpenseCategory.valueOf("OTHERS"));
        expense.setDescription("description");
        expense.setUser(user);

    }

    @AfterEach
    void tearDown() {
        mockStatic.close();
    }

    @Test
    void createExpense() {

        when(UserUtils.getUserLogged()).thenReturn(user);

        ExpenseRequestDTO request = new ExpenseRequestDTO(
                100.00F,
                1739382882000L,
                "OTHERS",
                "description"
        );
        ExpenseResponseDTO expense = expenseService.createExpense(request);

        verify(expenseRepository, times(1)).save(any(Expense.class));

        assertEquals(expense.description(), request.description());
    }

    @Test
    void getExpenseSuccess() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.ofNullable(expense));

        String id = expense.getId().toString();

        ExpenseResponseDTO expense_got = expenseService.getExpense(id);

        assertEquals(expense_got.id(), expense.getId());
    }

    @Test
    void updateExpense() {
    }

    @Test
    void deleteExpense() {
    }

    @Test
    void getPastWeekExpenses() {
    }

    @Test
    void getPastMonthExpenses() {
    }

    @Test
    void getPastThreeMonthsExpenses() {
    }

    @Test
    void getExpensesBetweenCustomDate() {
    }
}