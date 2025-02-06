package com.costa.expense_tracker_api.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {super("Invalid Credentials");}

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
