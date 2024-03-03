package com.manunin.score.mapper;

import com.manunin.score.dto.RoleDto;
import com.manunin.score.dto.SignupRequest;
import com.manunin.score.dto.UserDto;
import com.manunin.score.model.ERole;
import com.manunin.score.model.Role;
import com.manunin.score.model.User;
import com.manunin.score.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    private final RoleMapper roleMapper;


    public UserMapper(PasswordEncoder passwordEncoder, RoleMapper roleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.roleMapper = roleMapper;
    }

    public User fromDto(SignupRequest signUpRequest, final RoleRepository roleRepository) {
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName());
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER.getName())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> roleRepository.findByName(role).ifPresent(roles::add));
        }
        user.setRoles(roles);
        return user;
    }

    public User fromDto(final UserDto userDto) {
        User user = new User(userDto.getUsername(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getIsLocked());
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        Set<Role> roles = new HashSet<>();
        Set<RoleDto> rolesFromDto = userDto.getRoles();
        if (rolesFromDto != null) {
            rolesFromDto.forEach(role -> roles.add(roleMapper.fromDto(role)));
        }
        user.setRoles(roles);
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setIsLocked(user.isLocked());
        userDto.setRoles(user.getRoles().stream().map(roleMapper::toDto).collect(Collectors.toSet()));
        return userDto;
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }
}
