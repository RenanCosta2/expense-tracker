package com.costa.expense_tracker_api.controllers;

import com.costa.expense_tracker_api.domain.expense.Expense;
import com.costa.expense_tracker_api.domain.expense.ExpenseRequestDTO;
import com.costa.expense_tracker_api.domain.expense.ExpenseResponseDTO;
import com.costa.expense_tracker_api.repositories.ExpenseRepository;
import com.costa.expense_tracker_api.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@RequestBody ExpenseRequestDTO body){
        Expense newExpense = this.expenseService.createExpense(body);

        URI location = URI.create("/expenses/" + newExpense.getId());

        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO(newExpense.getId(),
                newExpense.getValue(),
                newExpense.getDate(),
                newExpense.getCategory().getCategory(),
                newExpense.getDescription());

        return ResponseEntity.created(location).body(expenseResponseDTO);
    }

}
