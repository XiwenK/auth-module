package com.manunin.score.controller;

import com.manunin.score.dto.MessageResponse;
import com.manunin.score.dto.SortDirection;
import com.manunin.score.dto.UserDto;
import com.manunin.score.exception.ResultType;
import com.manunin.score.mapper.UserMapper;
import com.manunin.score.service.UserService;
import com.manunin.score.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get User objects list")
    @GetMapping
    private ResponseEntity<List<UserDto>> getUsers(@RequestParam String filter,
                                                   @RequestParam int pageNumber,
                                                   @RequestParam int pageSize,
                                                   @RequestParam String sortField,
                                                   @RequestParam SortDirection sortDirection) {
        return ResponseEntity.ok(
                userMapper.toDtoList(
                        userService.findByFilterWithPagination(
                                filter,
                                PageRequest.of(
                                        pageNumber,
                                        pageSize,
                                        SortDirection.ASC.equals(sortDirection) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()
                                )
                        )
                )
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get User objects list count")
    @GetMapping("/count")
    private ResponseEntity<Long> getUsersCount(@RequestParam String filter) {
        return ResponseEntity.ok(userService.countByFilter(filter));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group.successfullyCreated"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Add user")
    @PostMapping
    private ResponseEntity<MessageResponse> addUser(@Valid @RequestBody UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            return RestUtils.response(ResultType.USERNAME_ALREADY_EXISTS);
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            return RestUtils.response(ResultType.EMAIL_ALREADY_EXISTS);
        }
        userService.addUser(userMapper.fromDto(userDto));
        return RestUtils.response(ResultType.USER_SUCCESSFULLY_CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Modify user")
    @PutMapping
    private ResponseEntity<MessageResponse> modifyUser(@Valid @RequestBody UserDto userDto) {
        userService.modifyUser(userMapper.fromDto(userDto));
        return RestUtils.response(ResultType.USER_SUCCESSFULLY_MODIFIED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteGroup(@PathVariable final Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
