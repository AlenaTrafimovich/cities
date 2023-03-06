package com.lena.application.dao;

import com.lena.application.model.entity.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDaoListener {
    private final PasswordEncoder encoder;

    public UserDaoListener() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @PrePersist
    @PreUpdate
    private void beforeSave(User user) {
        encodePassword(user);
    }

    private void encodePassword(User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
    }
}