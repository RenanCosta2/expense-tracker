package com.costa.expense_tracker_api.repositories;

import com.costa.expense_tracker_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
