package com.manunin.score.controller;

import com.manunin.score.dto.SignupDto;
import com.manunin.score.exception.ServiceException;
import com.manunin.score.mapper.UserMapper;
import com.manunin.score.model.User;
import com.manunin.score.repository.RoleRepository;
import com.manunin.score.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;


    public AuthController(final UserService userService,
                          final RoleRepository roleRepository,
                          final UserMapper userMapper) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is successfully signed up"),
            @ApiResponse(responseCode = "400", description = "One of Username already exists or Email already exists")
    })
    @Operation(summary = "User signup")
    @PostMapping("/signup")
    public User registerUser(@Valid @RequestBody final SignupDto signUpRequest) throws ServiceException {
        return userService.addUser(userMapper.fromDto(signUpRequest, roleRepository));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Number of users in the system"),
            @ApiResponse(responseCode = "400", description = "Error while getting number of users")
    })
    @GetMapping("/users/count")
    public Long getUsersCount() {
        return userService.getUsersCount();
    }
}
