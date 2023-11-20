package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.GroupDto;
import com.manunin.score.model.Group;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MapperConfiguration.class)
public interface GroupMapper {
    GroupDto toDto(Group group);
    Group fromDto(GroupDto group);
    List<GroupDto> toDtoList(List<Group> groupList);

    default GroupDto toRegFormGroup(final GroupDto group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setName(group.getName());
        groupDto.setDateTime(group.getDateTime());
        return groupDto;
    }
}
