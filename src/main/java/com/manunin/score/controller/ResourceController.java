package com.manunin.score.controller;

import com.manunin.score.dto.MessageResponse;
import com.manunin.score.dto.ResourceDto;
import com.manunin.score.dto.SortDirection;
import com.manunin.score.exception.ResultType;
import com.manunin.score.mapper.ResourceMapper;
import com.manunin.score.model.Resource;
import com.manunin.score.service.ResourceService;
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
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    private final ResourceMapper resourceMapper;

    public ResourceController(ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceService = resourceService;
        this.resourceMapper = resourceMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get resources list")
    @GetMapping
    private ResponseEntity<List<ResourceDto>> getResources(@RequestParam String string,
                                                           @RequestParam int pageNumber,
                                                           @RequestParam int pageSize,
                                                           @RequestParam String sortField,
                                                           @RequestParam SortDirection sortDirection) {
        List<Resource> groups = resourceService.findByStringWithPagination(string,
                PageRequest.of(pageNumber,
                        pageSize,
                        SortDirection.ASC.equals(sortDirection) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()));
        return ResponseEntity.ok(resourceMapper.toDtoList(groups));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resources count is loaded"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get resources count")
    @GetMapping("/resourcesCount")
    public ResponseEntity<Integer> getGroupsCount(@RequestParam String string) {
        return ResponseEntity.ok(resourceService.findByString(string).size());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group.successfullyCreated"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Create resource")
    @PostMapping
    private ResponseEntity<MessageResponse> createGroup(@Valid @RequestBody ResourceDto resourceDto) {
        if (resourceService.existsByName(resourceDto.getName())) {
            return RestUtils.response(ResultType.RESOURCE_NAME_ALREADY_EXISTS);
        }
        resourceService.updateResource(resourceMapper.fromDto(resourceDto));
        return RestUtils.response(ResultType.RESOURCE_SUCCESSFULLY_CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Update resource")
    @PutMapping
    private ResponseEntity<Void> updateGroup(@Valid @RequestBody ResourceDto resourceDto) {
        resourceService.updateResource(resourceMapper.fromDto(resourceDto));
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Delete resource")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteGroup(@PathVariable final Long id) {
        resourceService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
