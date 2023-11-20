package com.manunin.score.service;

import com.manunin.score.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User modifyUser(User user);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
    void deleteUser(Long id);
    List<User> findByStringWithPagination(String inputString, Pageable pageable);
    List<User> findByString(String inputString);
}
