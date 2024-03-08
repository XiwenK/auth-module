package com.manunin.score.service;

import com.manunin.score.exception.ServiceException;
import com.manunin.score.model.User;
import com.manunin.score.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.manunin.score.exception.ResultType.NOT_FOUND;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User modifyUser(final User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "exception.not-found.service.demo-object-not-found", user.getUsername()));
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setRoles(user.getRoles());
        userFromDb.setLocked(user.isLocked());
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
        userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "exception.not-found.service.demo-object-not-found", id));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<User> findByFilterWithPagination(final String filter, final Pageable pageable) {
        return userRepository.findAllByFilterWithPagination(filter, pageable).get().collect(Collectors.toList());
    }

    @Override
    public long countByFilter(final String filter) {
        return userRepository.countByFilter(filter);
    }

    @Override
    public List<User> findByString(final String inputString) {
        return userRepository.findAllByInputString(inputString);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "exception.not-found.service.demo-object-not-found", username));
    }
}
