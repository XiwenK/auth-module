package com.manunin.score.controller;

import com.manunin.score.dto.GroupDto;
import com.manunin.score.dto.GroupUrlDto;
import com.manunin.score.mapper.GroupMapper;
import com.manunin.score.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortUrls")
public class ShortUrlController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    public ShortUrlController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Long url found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get url by short url")
    @PostMapping("/getGroupUrl")
    public ResponseEntity<String> getUrl(@RequestBody GroupUrlDto groupUrlDto) {
        return groupService.getUrlByGroupId(groupUrlDto.getGroupId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Long url found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get url by short url")
    @PostMapping("/getGroupId")
    public ResponseEntity<GroupDto> getGroupId(@RequestBody GroupUrlDto groupUrlDto) {
        return groupService.getGroupNameByUrl(groupUrlDto.getUrl())
                .map(group -> ResponseEntity.ok(groupMapper.toRegFormGroup(groupMapper.toDto(group))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

}
