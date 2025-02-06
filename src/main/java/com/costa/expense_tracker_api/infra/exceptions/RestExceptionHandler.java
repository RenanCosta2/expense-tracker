package com.costa.expense_tracker_api.infra.exceptions;

import com.costa.expense_tracker_api.exceptions.ExpenseNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ExpenseNotFound.class)
    private ResponseEntity<RestErrorMessage> expenseNotFoundErrorHandler(ExpenseNotFound exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }
}
