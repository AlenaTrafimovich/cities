package com.lena.application.controller.dto;

import com.lena.application.model.entity.Role;

import java.util.Set;

public class UserResponseDto {

    private String name;
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
