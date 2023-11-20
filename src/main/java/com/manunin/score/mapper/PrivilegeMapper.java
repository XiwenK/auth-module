package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.PrivilegeDto;
import com.manunin.score.model.Privilege;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MapperConfiguration.class)
public interface PrivilegeMapper {

    PrivilegeDto toDto(Privilege space);

    List<PrivilegeDto> toDtoList(List<Privilege> spaces);

    @Mapping(target = "id", ignore = true)
    Privilege fromDto(PrivilegeDto space);
}
