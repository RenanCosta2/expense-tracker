package com.costa.expense_tracker_api.repositories;

import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should get User successfully from DB by login")
    void findByLoginSuccess() {
        User user = new User();
        user.setLogin("loginTest");
        user.setName("nameTest");
        user.setPassword("12345678");
        user.setRole(UserRole.USER);

        this.userRepository.save(user);

        Optional<UserDetails> foundedUser = this.userRepository.findByLogin(user.getLogin());

        assertTrue(foundedUser.isPresent());
    }

    @Test
    @DisplayName("Should not get User from DB by login when user not exists")
    void findByLoginFailure() {
        String login = "loginTest";

        Optional<UserDetails> foundedUser = this.userRepository.findByLogin(login);

        assertTrue(foundedUser.isEmpty());
    }
}