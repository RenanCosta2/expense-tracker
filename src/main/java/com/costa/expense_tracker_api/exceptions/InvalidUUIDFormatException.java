package com.costa.expense_tracker_api.exceptions;

public class InvalidUUIDFormatException extends RuntimeException {

    public InvalidUUIDFormatException() {super("Invalid UUID format");}

    public InvalidUUIDFormatException(String message) {
        super(message);
    }
}
