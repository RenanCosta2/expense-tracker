package com.costa.expense_tracker_api.controllers;

import com.costa.expense_tracker_api.domain.user.*;
import com.costa.expense_tracker_api.exceptions.InvalidCredentialsException;
import com.costa.expense_tracker_api.infra.security.TokenService;
import com.costa.expense_tracker_api.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setLogin(data.login());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(data.role());

        userRepository.save(newUser);

        UserResponseDTO userResponse = new UserResponseDTO(newUser.getId(), newUser.getName(), newUser.getLogin(), newUser.getRole().getRole());

        return ResponseEntity.ok(userResponse);
    }
}