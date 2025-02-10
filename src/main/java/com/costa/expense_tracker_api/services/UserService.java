package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.user.*;
import com.costa.expense_tracker_api.exceptions.InvalidCredentialsException;
import com.costa.expense_tracker_api.exceptions.UserAlreadyExistsException;
import com.costa.expense_tracker_api.infra.security.TokenService;
import com.costa.expense_tracker_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserResponseDTO createUser(RegisterDTO data){

        if(this.userRepository.findByLogin(data.login()).isPresent()) throw new UserAlreadyExistsException();

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setLogin(data.login());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(data.role());

        userRepository.save(newUser);

        return new UserResponseDTO(newUser.getId(), newUser.getName(), newUser.getLogin(), newUser.getRole().getRole());

    }

    public LoginResponseDTO loginUser(AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        try{
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return new LoginResponseDTO(token);

        } catch (InternalAuthenticationServiceException exception) {
            throw new InvalidCredentialsException();
        }
    }

}
