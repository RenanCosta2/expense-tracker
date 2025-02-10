package com.costa.expense_tracker_api.controllers;

import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.dtos.ExpenseResponseDTO;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import com.costa.expense_tracker_api.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@RequestBody @Valid ExpenseRequestDTO body){
        ExpenseResponseDTO newExpense = this.expenseService.createExpense(body);

        URI location = URI.create("/expense/" + newExpense.id());

        return ResponseEntity.created(location).body(newExpense);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> getExpense(@PathVariable String expenseId){

        ExpenseResponseDTO expense = this.expenseService.getExpense(expenseId);

        return ResponseEntity.ok(expense);

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
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable String expenseId, @RequestBody ExpenseRequestDTO data){

        ExpenseResponseDTO expense = this.expenseService.updateExpense(expenseId, data);

        return ResponseEntity.ok(expense);

    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String expenseId){

        this.expenseService.deleteExpense(expenseId);

        return ResponseEntity.noContent().build();
    }
}
