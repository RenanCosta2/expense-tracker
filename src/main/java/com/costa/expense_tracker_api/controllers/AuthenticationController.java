package com.costa.expense_tracker_api.controllers;

import com.costa.expense_tracker_api.domain.user.*;
import com.costa.expense_tracker_api.exceptions.InvalidCredentialsException;
import com.costa.expense_tracker_api.exceptions.UserAlreadyExistsException;
import com.costa.expense_tracker_api.infra.security.TokenService;
import com.costa.expense_tracker_api.repositories.UserRepository;
import com.costa.expense_tracker_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        try{
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (InternalAuthenticationServiceException exception) {
            throw new InvalidCredentialsException();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterDTO data){

        UserResponseDTO userResponse = this.userService.createUser(data);

        return ResponseEntity.ok(userResponse);
    }
}