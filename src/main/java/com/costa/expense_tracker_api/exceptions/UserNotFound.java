package com.costa.expense_tracker_api.exceptions;

public class UserNotFound extends RuntimeException {

    public UserNotFound() {super("User not found");}

    public UserNotFound(String message) {
        super(message);
    }
}
