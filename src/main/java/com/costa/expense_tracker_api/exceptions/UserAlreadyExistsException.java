package com.costa.expense_tracker_api.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException() {super("User already exists.");}

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
