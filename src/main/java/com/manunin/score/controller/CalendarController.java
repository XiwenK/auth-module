package com.manunin.score.controller;

import com.manunin.score.dto.CalendarDto;
import com.manunin.score.mapper.CalendarMapper;
import com.manunin.score.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calendars")
public class CalendarController {
    private final CalendarService calendarService;
    private final CalendarMapper calendarMapper;

    public CalendarController(CalendarService calendarService, CalendarMapper calendarMapper) {
        this.calendarService = calendarService;
        this.calendarMapper = calendarMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get calendars list")
    @GetMapping
    public List<CalendarDto> getCalendars() {
        return calendarMapper.toDtoList(calendarService.getCalendars());
    }
}
