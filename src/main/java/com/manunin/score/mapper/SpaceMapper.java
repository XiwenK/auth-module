package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.SpaceDto;
import com.manunin.score.model.Space;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MapperConfiguration.class)
public interface SpaceMapper {
    SpaceDto toDto(Space space);
    List<SpaceDto> toDtoList(List<Space> spaces);

    @Mapping(target = "id", ignore = true)
    Space fromDto(SpaceDto space);
}
