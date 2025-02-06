package com.costa.expense_tracker_api.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {super("Invalid Token");}

    public InvalidTokenException(String message) {
        super(message);
    }
}
