package com.lena.application.service.impl;

import com.lena.application.controller.dto.UserAddRequest;
import com.lena.application.controller.dto.UserEditRoleRequest;
import com.lena.application.exception.ResourceNotFoundException;
import com.lena.application.exception.UserAlreadyExistException;
import com.lena.application.model.entity.User;
import com.lena.application.repository.UserRepository;
import com.lena.application.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public User getUserByCredentials(String userName, String password) {
        User user = repository.findUserByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        return repository.findUserByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User addUser(UserAddRequest request) {
        if (repository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User already exist");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return repository.save(user);
    }

    @Override
    public User updateUserRole(String userName, @NotNull UserEditRoleRequest request) {
        User user = getUserByUserName(userName);
        user.setRoles(request.getRoles());
        return repository.save(user);
    }
}
