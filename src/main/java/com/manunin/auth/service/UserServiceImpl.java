package com.manunin.auth.service;

import com.manunin.auth.exception.ErrorCode;
import com.manunin.auth.exception.ServiceException;
import com.manunin.auth.model.ERole;
import com.manunin.auth.model.Role;
import com.manunin.auth.model.User;
import com.manunin.auth.repository.RoleRepository;
import com.manunin.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class UserServiceImpl implements UserService {

    public static final String EXCEPTION_USERNAME_EXISTS = "exception.usernameExists";
    public static final String EXCEPTION_EMAIL_EXISTS = "exception.emailExists";
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(final UserRepository userRepository,
                           final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User addUser(final User user) throws ServiceException {
        if (existsByUsername(user.getUsername())) {
            throw new ServiceException(ErrorCode.BAD_REQUEST_PARAMS, EXCEPTION_USERNAME_EXISTS);
        }
        if (existsByEmail(user.getEmail())) {
            throw new ServiceException(ErrorCode.BAD_REQUEST_PARAMS, EXCEPTION_EMAIL_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(ERole.USER.getName())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRoles(Set.of(userRole));
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
    public User findByEmail(String email) throws ServiceException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(ErrorCode.BAD_REQUEST_PARAMS, "exception.user.notFound"));
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }
}
