package com.manunin.auth.mapper;

import com.manunin.auth.dto.SignupDto;
import com.manunin.auth.model.ERole;
import com.manunin.auth.model.Role;
import com.manunin.auth.model.User;
import com.manunin.auth.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

    public User fromDto(final SignupDto signUpDto, final RoleRepository roleRepository) {
        User user = new User(signUpDto.getUsername(),
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getFirstName(),
                signUpDto.getLastName());
        //set roles
        Set<String> roles = signUpDto.getRoles();
        if (roles != null) {
            roles.forEach(role -> {
                Role userRole = roleRepository.findByName(ERole.valueOf(role).getName())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.getRoles().add(userRole);
            });
        }
        return user;
    }
}
