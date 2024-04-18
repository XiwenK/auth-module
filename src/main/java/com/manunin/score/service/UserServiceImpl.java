package com.manunin.score.service;

import com.manunin.score.exception.ErrorCode;
import com.manunin.score.exception.ServiceException;
import com.manunin.score.model.User;
import com.manunin.score.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {

    public static final String EXCEPTION_USERNAME_EXISTS = "exception.usernameExists";
    public static final String EXCEPTION_EMAIL_EXISTS = "exception.emailExists";
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(final User user) throws ServiceException {
        if (existsByUsername(user.getUsername())) {
            throw new ServiceException(ErrorCode.BAD_REQUEST_PARAMS, EXCEPTION_USERNAME_EXISTS);
        }
        if (existsByEmail(user.getEmail())) {
            throw new ServiceException(ErrorCode.BAD_REQUEST_PARAMS, EXCEPTION_EMAIL_EXISTS);
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean existsByUsername(final String name) {
        return userRepository.existsByUsername(name);
    }

    @Override
    @Transactional
    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByUsername(String username) throws ServiceException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.BAD_REQUEST_PARAMS, "exception.user.notFound"));
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }
}
