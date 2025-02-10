package com.costa.expense_tracker_api.domain.user.dtos;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String login, String role) {
}
