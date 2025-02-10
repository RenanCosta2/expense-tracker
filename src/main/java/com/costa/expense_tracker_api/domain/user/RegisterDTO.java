package com.costa.expense_tracker_api.domain.user;

import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotNull(message = "User name is required.") String name,
        @NotNull(message = "User login is required.") String login,
        @NotNull(message = "User password is required.") String password,
        @NotNull(message = "User role is required.") UserRole role) {
}
