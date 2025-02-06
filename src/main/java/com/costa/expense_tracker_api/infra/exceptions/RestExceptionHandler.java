package com.costa.expense_tracker_api.infra.exceptions;

import com.costa.expense_tracker_api.exceptions.*;
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

    @ExceptionHandler(InvalidUUIDFormatException.class)
    private ResponseEntity<RestErrorMessage> invalidUUIDFormatHandler(InvalidUUIDFormatException exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }

    @ExceptionHandler(UserNotLoggedException.class)
    private ResponseEntity<RestErrorMessage> userNotLoggedHandler(UserNotLoggedException exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }

    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<RestErrorMessage> invalidTokenHandler(InvalidTokenException exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    private ResponseEntity<RestErrorMessage> invalidCredentialsHandler(InvalidCredentialsException exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<RestErrorMessage> userNotFoundErrorHandler(UserNotFound exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyExistsHandler(UserAlreadyExistsException exception){
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(restErrorMessage.getHttpStatus()).body(restErrorMessage);
    }
}
