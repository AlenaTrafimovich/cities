package com.lena.application.service;

import com.lena.application.controller.dto.UserAddRequest;
import com.lena.application.controller.dto.UserEditRoleRequest;
import com.lena.application.model.entity.User;

/**
 * Service to manipulate user data
 */
public interface UserService {

    /**
     * Get UserResponseDto by userName
     *
     * @param userName name of user
     * @param password password
     * @return {@link User} information about user
     */
    User getUserByCredentials(String userName, String password);

    /**
     * Get user by username
     *
     * @param userName name of user
     * @return {@link User} information about user;
     */
    User getUserByUserName(String userName);

    /**
     * Add new user
     *
     * @param request user data
     * @return {@link User} information about user
     */
    User addUser(UserAddRequest request);

    /**
     * Update user
     *
     * @param userName name of user
     * @param request  new information about user role
     * @return {@link User} information about user
     */
    User updateUserRole(String userName, UserEditRoleRequest request);
}
