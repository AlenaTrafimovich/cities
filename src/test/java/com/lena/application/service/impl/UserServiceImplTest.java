package com.lena.application.service.impl;

import com.lena.application.controller.dto.UserAddRequest;
import com.lena.application.controller.dto.UserEditRoleRequest;
import com.lena.application.exception.ResourceNotFoundException;
import com.lena.application.exception.UserAlreadyExistException;
import com.lena.application.model.entity.Role;
import com.lena.application.model.entity.User;
import com.lena.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("123");
    }

    @Test
    void getUserByCredentials_UserExist_returnUser() {
        User expected = createUser(1L, "admin", "123");

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        var actualUser = userService.getUserByCredentials("admin", "123");

        assertEquals(expected, actualUser);
        verify(userRepository).findUserByUsername("admin");
        verify(passwordEncoder).matches(expected.getPassword(), actualUser.getPassword());
    }

    @Test
    void getUserByCredentials_UserNotExist_returnUser() {
        when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.getUserByCredentials("user", "password"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getUserByUserName_UserExist_returnUser() {
        User expected = createUser(1L, "admin", "123");

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.of(user));

        var actualUser = userService.getUserByUserName("admin");

        assertEquals(expected, actualUser);
        verify(userRepository).findUserByUsername("admin");
    }

    @Test
    void getUserByUserName_UserNotExist_returnException() {
        when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.getUserByUserName("user"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void addUser_AlreadyExist_ReturnException() {
        UserAddRequest request = new UserAddRequest();
        request.setUserName("admin");
        request.setPassword("123");

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.of(user));

        var exception = assertThrows(UserAlreadyExistException.class, () ->
                userService.addUser(request));

        assertEquals("User already exist", exception.getMessage());
    }

    @Test
    void addUser_NotExist_ReturnUser() {
        UserAddRequest request = new UserAddRequest();
        request.setUserName("admin");
        request.setPassword("123");

        User expected = createUser(1L, "admin", "123");

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(expected);

        var actualUser = userService.addUser(request);

        assertEquals(expected, actualUser);
    }

    @Test
    void updateUserRole_() {
        UserEditRoleRequest request = new UserEditRoleRequest();
        request.setRoles(Set.of(Role.ROLE_ALLOW_EDIT));

        User expected = createUser(1L, "admin", "123");
        expected.setRoles(Set.of(Role.ROLE_ALLOW_EDIT));

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        var actualUser = userService.updateUserRole("admin", request);

        assertEquals(expected, actualUser);
        verify(userRepository).findUserByUsername("admin");
        verify(userRepository).save(expected);
    }

    private User createUser(Long id, String name, String password) {
        User created = new User();
        created.setId(id);
        created.setUsername(name);
        created.setPassword(password);
        return created;
    }
}
