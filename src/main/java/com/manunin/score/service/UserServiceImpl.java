package com.manunin.score.service;

import com.manunin.score.exception.ServiceException;
import com.manunin.score.model.User;
import com.manunin.score.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.manunin.score.exception.ResultType.NOT_FOUND;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(final User user) {
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "exception.not-found.service.demo-object-not-found", username));
    }
}
