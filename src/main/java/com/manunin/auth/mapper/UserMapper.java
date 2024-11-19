package com.manunin.auth.mapper;

import com.manunin.auth.dto.SignupDTO;
import com.manunin.auth.model.ERole;
import com.manunin.auth.model.Role;
import com.manunin.auth.model.User;
import com.manunin.auth.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

    public User fromDto(final SignupDTO signUpDto, final RoleRepository roleRepository) {
        User user = createUser(signUpDto);
        addRoles(roleRepository, user, signUpDto.getRoles());
        return user;
    }

    private static User createUser(final SignupDTO signUpDto) {
        return new User(signUpDto.getUsername(),
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getFirstName(),
                signUpDto.getLastName());
    }

    private static void addRoles(final RoleRepository roleRepository, final User user, final Set<String> roles) {
        if (roles == null) return;
        roles.forEach(role -> user.addRole(getRoleFromRepository(roleRepository, role)));
    }

    private static Role getRoleFromRepository(final RoleRepository roleRepository, final String role) {
        return roleRepository.findByName(ERole.valueOf(role).getName())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}
