package com.manunin.score.controller;

import com.manunin.score.dto.LoginRequest;
import com.manunin.score.dto.LoginResponse;
import com.manunin.score.dto.MessageResponse;
import com.manunin.score.dto.SignupRequest;
import com.manunin.score.exception.ResultType;
import com.manunin.score.mapper.UserMapper;
import com.manunin.score.repository.RoleRepository;
import com.manunin.score.secutiry.jwt.JwtTokenProvider;
import com.manunin.score.service.UserDetailsImpl;
import com.manunin.score.service.UserService;
import com.manunin.score.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    public AuthController(JwtTokenProvider jwtTokenProvider,
                          UserService userService,
                          RoleRepository roleRepository,
                          UserMapper userMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

//    @CrossOrigin(origins = "http://localhost:9002")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User is signed in"),
//            @ApiResponse(responseCode = "400", description = "Bad request")
//    })
//    @Operation(summary = "User signin")
//    @PostMapping("/signin")
//    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
////        Authentication authentication = authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//////        String jwt = jwtTokenProvider.generateToken(authentication);
////
////        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
////        List<String> roles = userDetails.getAuthorities().stream()
////                .map(GrantedAuthority::getAuthority)
////                .collect(Collectors.toList());
////        return ResponseEntity.ok(new LoginResponse(
////                userDetails.getId(),
////                userDetails.getUsername(),
////                userDetails.getEmail(),
////                jwt,
////                roles));
//    }

//    @CrossOrigin(origins = "http://localhost:8081")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is successfully signed up"),
            @ApiResponse(responseCode = "400", description = "One of Username already exists or Email already exists")
    })
    @Operation(summary = "User signup")
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return RestUtils.response(ResultType.USERNAME_ALREADY_EXISTS);
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return RestUtils.response(ResultType.EMAIL_ALREADY_EXISTS);
        }
        userService.addUser(userMapper.fromDto(signUpRequest, roleRepository));
        return RestUtils.response(ResultType.USER_SUCCESSFULLY_CREATED);
    }
}
