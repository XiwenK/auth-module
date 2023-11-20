package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.UserDto;
import com.manunin.score.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserUniverseMapper {
    User fromDto(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);
    List<User> fromDtoList(List<UserDto> userDtoList);

    @Mapping(target = "password", ignore = true)
    List<UserDto> toDtoList(List<User> userList);

}
