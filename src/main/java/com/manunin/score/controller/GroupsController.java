package com.manunin.score.controller;

import com.manunin.score.dto.GroupDto;
import com.manunin.score.dto.MessageResponse;
import com.manunin.score.dto.SortDirection;
import com.manunin.score.exception.ResultType;
import com.manunin.score.mapper.GroupMapper;
import com.manunin.score.model.Group;
import com.manunin.score.service.GroupService;
import com.manunin.score.service.GroupStudentService;
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
@RequestMapping("/groups")
public class GroupsController {

    private final GroupService groupService;

    private final GroupStudentService groupStudentService;

    private final GroupMapper groupMapper;

    public GroupsController(final GroupService groupService, GroupStudentService groupStudentService, final GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupStudentService = groupStudentService;
        this.groupMapper = groupMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get Group objects list")
    @GetMapping
    private ResponseEntity<List<GroupDto>> getGroups(@RequestParam String string,
                                                     @RequestParam int pageNumber,
                                                     @RequestParam int pageSize,
                                                     @RequestParam String sortField,
                                                     @RequestParam SortDirection sortDirection) {
        List<Group> groups = groupService.findByStringWithPagination(string,
                PageRequest.of(pageNumber,
                        pageSize,
                        SortDirection.ASC.equals(sortDirection) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()));
        return ResponseEntity.ok(groupMapper.toDtoList(groups));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groups count is loaded"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get groups count")
    @GetMapping("/groupsCount")
    public ResponseEntity<Integer> getGroupsCount(@RequestParam String string) {
        return ResponseEntity.ok(groupService.findByString(string).size());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group.successfullyCreated"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Create group")
    @PostMapping
    private ResponseEntity<MessageResponse> createGroup(@Valid @RequestBody GroupDto groupDto) {
        if (groupService.existsByName(groupDto.getName())) {
            return RestUtils.response(ResultType.GROUP_NAME_ALREADY_EXISTS);
        }
        groupService.createGroup(groupMapper.fromDto(groupDto));
        return RestUtils.response(ResultType.GROUP_SUCCESSFULLY_CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Update group")
    @PutMapping
    private ResponseEntity<Void> updateGroup(@Valid @RequestBody GroupDto groupDto) {
        Group group = groupMapper.fromDto(groupDto);
        group.setStudents(groupStudentService.findByGroupId(group.getId()));
        groupService.updateGroup(group);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Delete group")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteGroup(@PathVariable final Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

}
