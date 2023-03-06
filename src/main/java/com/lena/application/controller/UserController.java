package com.lena.application.controller;

import com.lena.application.controller.dto.LoginRequest;
import com.lena.application.controller.dto.UserAddRequest;
import com.lena.application.controller.dto.UserEditRoleRequest;
import com.lena.application.controller.dto.UserResponseDto;
import com.lena.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lena.application.mapper.UserMapper.toUserResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginRequest request) {
        UserResponseDto dto =
                toUserResponse(userService.getUserByCredentials(request.getUsername(), request.getPassword()));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("userName") String userName) {
        return new ResponseEntity<>(toUserResponse(userService.getUserByUserName(userName)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserAddRequest request) {
        return new ResponseEntity<>(toUserResponse(userService.addUser(request)), HttpStatus.OK);
    }

    @PutMapping("/{userName}")
    @Secured("ROLE_ALLOW_EDIT")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userName") String username,
                                                      @RequestBody UserEditRoleRequest request) {
        return new ResponseEntity<>(toUserResponse(userService.updateUserRole(username, request)), HttpStatus.OK);
    }
}
