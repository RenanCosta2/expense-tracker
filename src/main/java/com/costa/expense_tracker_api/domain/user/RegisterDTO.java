package com.costa.expense_tracker_api.domain.user;

public record RegisterDTO(String name, String login, String password, UserRole role) {
}
