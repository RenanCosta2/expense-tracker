package com.costa.expense_tracker_api.services;

import com.costa.expense_tracker_api.domain.user.RegisterDTO;
import com.costa.expense_tracker_api.domain.user.User;
import com.costa.expense_tracker_api.domain.user.UserResponseDTO;
import com.costa.expense_tracker_api.exceptions.UserAlreadyExistsException;
import com.costa.expense_tracker_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
