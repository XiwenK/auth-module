package com.manunin.score.mapper;

import com.manunin.score.dto.SignupRequest;
import com.manunin.score.model.ERole;
import com.manunin.score.model.Role;
import com.manunin.score.model.User;
import com.manunin.score.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User fromDto(SignupRequest signUpRequest, final RoleRepository roleRepository) {
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.USER.getName())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        return user;
    }
}
