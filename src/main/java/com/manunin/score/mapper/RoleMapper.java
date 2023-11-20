package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.PrivilegeDto;
import com.manunin.score.dto.RoleDto;
import com.manunin.score.model.Privilege;
import com.manunin.score.model.Role;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface RoleMapper {

    RoleDto toDto(Role space);

    Role fromDto(RoleDto space);

    List<RoleDto> toDtoList(List<Role> spaces);

    List<Role> fromDtoList(List<RoleDto> roleDto);

    PrivilegeDto toPrivilegeDto(Privilege privilege);

    Privilege fromPrivilegeDto(PrivilegeDto privilegeDto);

    List<PrivilegeDto> toPrivilegeDtoSet(List<Privilege> privileges);

    List<Privilege> fromPrivilegeDtoSet(List<PrivilegeDto> privilegeDtos);
}