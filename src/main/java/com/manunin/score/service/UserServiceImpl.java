package com.manunin.score.service;

import com.manunin.score.exception.ServiceException;
import com.manunin.score.model.User;
import com.manunin.score.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.manunin.score.exception.ResultType.NOT_FOUND;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(final User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User modifyUser(final User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "exception.not-found.service.demo-object-not-found", user.getUsername()));
        userFromDb.setRoles(user.getRoles());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setLocked(user.isLocked());
        userFromDb.setSpaces(user.getSpaces());
        return userRepository.save(userFromDb);
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
    @Transactional
    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<User> findByStringWithPagination(final String inputString, final Pageable pageable) {
        return userRepository.findAllByInputStringWithPagination(inputString, pageable).get().collect(Collectors.toList());
    }

    @Override
    public List<User> findByString(final String inputString) {
        return userRepository.findAllByInputString(inputString);
    }
}
