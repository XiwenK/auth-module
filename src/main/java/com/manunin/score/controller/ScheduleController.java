package com.manunin.score.controller;

import com.manunin.score.dto.ScheduleDto;
import com.manunin.score.mapper.ScheduleMapper;
import com.manunin.score.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final ScheduleMapper scheduleMapper;

    public ScheduleController(final ScheduleService scheduleService, final ScheduleMapper groupMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = groupMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get Schedule objects list")
    @GetMapping
    private List<ScheduleDto> getGroups() {
        return scheduleMapper.toDtoList(scheduleService.getSchedules());
    }
}
