package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseCategory;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseResponseDTO;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserRole;
import com.costa.expense_tracker_api.domain.user.UserUtils;
import com.costa.expense_tracker_api.exceptions.ExpenseNotFound;
import com.costa.expense_tracker_api.exceptions.InvalidUUIDFormatException;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
    @DisplayName("Should create expense successfully")
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
    @DisplayName("Should get expense by ID and User successfully")
    void getExpenseSuccess() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.ofNullable(expense));

        String id = expense.getId().toString();

        ExpenseResponseDTO expense_got = expenseService.getExpense(id);

        assertEquals(expense_got.id(), expense.getId());
    }

    @Test
    @DisplayName("Should throw InvalidUUIDFormatException when UUID format is invalid")
    void getExpenseInvalidUUIDFormat() {

        InvalidUUIDFormatException thrown = assertThrows(InvalidUUIDFormatException.class, () -> {
            String id = "550e8400-e29b-41d4-a7164466554";
            expenseService.getExpense(id);

        });

        assertEquals("Invalid UUID format", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw ExpenseNotFound exception when the expense is not found")
    void getExpenseFailureNotFound() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.empty());

        ExpenseNotFound thrown = assertThrows(ExpenseNotFound.class, () ->{
            String id = UUID.randomUUID().toString();

            expenseService.getExpense(id);

        });

        assertEquals("Expense not found", thrown.getMessage());
    }

    @Test
    void updateExpense() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.ofNullable(expense));

        String id = expense.getId().toString();

        ExpenseRequestDTO update = new ExpenseRequestDTO(
                200.00F,
                null,
                null,
                "new description"
        );

        ExpenseResponseDTO expense_got = expenseService.updateExpense(id, update);

        assertEquals(expense_got.value(), update.value());
        assertEquals(expense_got.description(), update.description());
    }

    @Test
    @DisplayName("Should throw InvalidUUIDFormatException when UUID format is invalid")
    void updateExpenseInvalidUUIDFormat() {

        InvalidUUIDFormatException thrown = assertThrows(InvalidUUIDFormatException.class, () -> {
            String id = "550e8400-e29b-41d4-a7164466554";
            ExpenseRequestDTO update = new ExpenseRequestDTO(
                    200.00F,
                    null,
                    null,
                    "new description"
            );
            expenseService.updateExpense(id, update);

        });

        assertEquals("Invalid UUID format", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw ExpenseNotFound exception when the expense is not found")
    void updateExpenseFailureNotFound() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.empty());

        ExpenseNotFound thrown = assertThrows(ExpenseNotFound.class, () ->{
            String id = UUID.randomUUID().toString();
            ExpenseRequestDTO update = new ExpenseRequestDTO(
                    200.00F,
                    null,
                    null,
                    "new description"
            );

            expenseService.updateExpense(id, update);

        });

        assertEquals("Expense not found", thrown.getMessage());
    }

    @Test
    @DisplayName("Should delete expense successfully")
    void deleteExpenseSuccess() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.ofNullable(expense));

        String id = expense.getId().toString();

        expenseService.deleteExpense(id);

        verify(expenseRepository, times(1)).deleteById(any(UUID.class));

    }

    @Test
    @DisplayName("Should throw InvalidUUIDFormatException when UUID format is invalid")
    void deleteExpenseInvalidUUIDFormat() {
        InvalidUUIDFormatException thrown = assertThrows(InvalidUUIDFormatException.class, () -> {
            String id = "550e8400-e29b-41d4-a7164466554";
            expenseService.deleteExpense(id);

        });

        assertEquals("Invalid UUID format", thrown.getMessage());

    }

    @Test
    @DisplayName("Should throw ExpenseNotFound exception when the expense is not found")
    void deleteExpenseFailureNotFound() {
        when(UserUtils.getUserLogged()).thenReturn(user);

        when(expenseRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.empty());

        ExpenseNotFound thrown = assertThrows(ExpenseNotFound.class, () ->{
            String id = UUID.randomUUID().toString();

            expenseService.deleteExpense(id);

        });

        assertEquals("Expense not found", thrown.getMessage());
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