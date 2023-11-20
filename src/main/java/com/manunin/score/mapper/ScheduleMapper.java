package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.ScheduleDto;
import com.manunin.score.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MapperConfiguration.class)
public interface ScheduleMapper {

    ScheduleDto toDto(Schedule schedule);

    @Mapping(target = "id", ignore = true)
    Schedule fromDto(ScheduleDto group);

    List<ScheduleDto> toDtoList(List<Schedule> groupList);
}
