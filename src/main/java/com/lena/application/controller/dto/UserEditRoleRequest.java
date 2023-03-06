package com.lena.application.controller.dto;

import com.lena.application.model.entity.Role;

import java.util.Set;

public class UserEditRoleRequest {
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
