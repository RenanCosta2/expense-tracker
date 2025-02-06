package com.costa.expense_tracker_api.controllers;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.ExpenseResponseDTO;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import com.costa.expense_tracker_api.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@RequestBody ExpenseRequestDTO body){
        ExpenseResponseDTO newExpense = this.expenseService.createExpense(body);

        URI location = URI.create("/expense/" + newExpense.id());

        return ResponseEntity.created(location).body(newExpense);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> getExpense(@PathVariable UUID expenseId){

        try{
            ExpenseResponseDTO expense = this.expenseService.getExpense(expenseId);

            return ResponseEntity.ok(expense);
        } catch (IllegalArgumentException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/past-week")
    public ResponseEntity<List<ExpenseResponseDTO>> getPastWeekExpenses(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        List<ExpenseResponseDTO> pastWeekExpenses = this.expenseService.getPastWeekExpenses(page, size);
        return ResponseEntity.ok(pastWeekExpenses);
    }

    @GetMapping("past-month")
    public ResponseEntity<List<ExpenseResponseDTO>> getPastMonthExpenses(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        List<ExpenseResponseDTO> pastMonthExpenses = this.expenseService.getPastMonthExpenses(page, size);
        return ResponseEntity.ok(pastMonthExpenses);
    }

    @GetMapping("past-three-months")
    public ResponseEntity<List<ExpenseResponseDTO>> getPastThreeMonthsExpenses(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size){
        List<ExpenseResponseDTO> pastThreeMonthsExpenses = this.expenseService.getPastThreeMonthsExpenses(page, size);
        return ResponseEntity.ok(pastThreeMonthsExpenses);
    }

    @GetMapping("past-custom")
    public ResponseEntity<List<ExpenseResponseDTO>> getPastExpensesBetweenCustomDate(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam Long startDate,
                                                                    @RequestParam Long endDate){
        Date start = new Date(startDate);
        Date end = new Date(endDate);

        List<ExpenseResponseDTO> pastExpensesBetweenCustomDate = this.expenseService.getExpensesBetweenCustomDate(
                page,
                size,
                start,
                end
        );

        return ResponseEntity.ok(pastExpensesBetweenCustomDate);
    }

    @PatchMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable UUID expenseId, @RequestBody ExpenseRequestDTO data){
        ExpenseResponseDTO expense = this.expenseService.updateExpense(expenseId, data);

        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId){
        try{
            this.expenseService.deleteExpense(expenseId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception){
            return ResponseEntity.notFound().build();
        }
    }
}
