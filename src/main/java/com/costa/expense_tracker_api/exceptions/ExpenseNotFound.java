package com.costa.expense_tracker_api.exceptions;

public class ExpenseNotFound extends RuntimeException{

    public ExpenseNotFound() {super("Expense not found");}

    public ExpenseNotFound(String message) {super(message);}
}
