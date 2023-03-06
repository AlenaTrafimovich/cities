package com.lena.application.mapper;

import com.lena.application.controller.dto.UserResponseDto;

public class UserMapper {

    public static UserResponseDto toUserResponse(com.lena.application.model.entity.User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setName(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
