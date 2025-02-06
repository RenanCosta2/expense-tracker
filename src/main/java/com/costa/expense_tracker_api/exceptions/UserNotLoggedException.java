package com.costa.expense_tracker_api.exceptions;

public class UserNotLoggedException extends RuntimeException {

    public UserNotLoggedException() {super("User not logged");}

    public UserNotLoggedException(String message) {
        super(message);
    }
}
