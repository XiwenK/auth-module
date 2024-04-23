package com.manunin.auth.service;

import com.manunin.auth.exception.ServiceException;
import com.manunin.auth.model.User;

public interface UserService {
    User addUser(User user) throws ServiceException;
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
    User findByUsername(String username) throws ServiceException;
    User findByEmail(String email) throws ServiceException;
    Long getUsersCount();
}
