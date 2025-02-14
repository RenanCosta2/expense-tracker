package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserRole;
import com.costa.expense_tracker_api.domain.user.dtos.RegisterDTO;
import com.costa.expense_tracker_api.domain.user.dtos.UserResponseDTO;
import com.costa.expense_tracker_api.exceptions.UserAlreadyExistsException;
import com.costa.expense_tracker_api.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should create user successfully")
    void createUserSuccess() {
        when(userRepository.findByLogin(any(String.class))).thenReturn(Optional.empty());

        RegisterDTO register = new RegisterDTO(
          "Test",
          "login",
          "12345678",
          UserRole.USER
        );

        UserResponseDTO response = userService.createUser(register);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(register.login(), response.login());
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when user already exists")
    void createUserFailureUserExists() {
        UserDetails userMock = mock(UserDetails.class);
        when(userRepository.findByLogin(any(String.class))).thenReturn(Optional.ofNullable(userMock));

        UserAlreadyExistsException thrown = assertThrows(UserAlreadyExistsException.class, () ->{
            RegisterDTO register = new RegisterDTO(
              "Test",
              "login",
              "12345678",
              UserRole.USER
            );

            userService.createUser(register);

        });

        assertEquals("User already exists.", thrown.getMessage());
    }

    @Test
    void loginUser() {
    }
}