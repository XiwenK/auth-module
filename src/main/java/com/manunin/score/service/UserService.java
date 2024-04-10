package com.manunin.score.service;

import com.manunin.score.model.User;

public interface UserService {
    User addUser(User user);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
    User findByUsername(String username);
}
