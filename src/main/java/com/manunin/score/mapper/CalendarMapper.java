package com.manunin.score.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manunin.score.dto.CalendarDto;
import com.manunin.score.model.Calendar;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();
    public CalendarDto toDto(final Calendar calendar) {
        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setId(calendar.getId());
        calendarDto.setName(calendar.getName());
        calendarDto.setDescription(calendar.getDescription());
        try {
            if (StringUtils.isNotBlank(calendar.getData())) {
                calendarDto.setData(objectMapper.readTree(calendar.getData()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        calendarDto.setSystem(calendar.isSystem());
        return calendarDto;
    }
    public List<CalendarDto> toDtoList(final List<Calendar> calendarList){
        List<CalendarDto> calendarDtoList = new ArrayList<>();
        for (Calendar calendar : calendarList) {
            calendarDtoList.add(toDto(calendar));
        }
        return calendarDtoList;
    }
    public Calendar fromDto(CalendarDto calendarDto){
        Calendar calendar = new Calendar();
        calendar.setId(calendarDto.getId());
        calendar.setName(calendarDto.getName());
        calendar.setDescription(calendarDto.getDescription());
        if (calendarDto.getData() != null) {
            calendar.setData(calendarDto.getData().toString());
        }
        calendar.setSystem(calendarDto.isSystem());
        return calendar;
    }
}
