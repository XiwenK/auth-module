package com.manunin.score.mapper;

import com.manunin.score.dto.ResourceDto;
import com.manunin.score.model.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceMapper {

    private final CalendarMapper calendarMapper;

    public ResourceMapper(final CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }
    public ResourceDto toDto(final Resource resource){
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(resource.getId());
        resourceDto.setName(resource.getName());
        resourceDto.setDescription(resource.getDescription());
        resourceDto.setType(resource.getType());
        resourceDto.setActive(resource.isActive());
        resourceDto.setCalendar(calendarMapper.toDto(resource.getCalendar()));
        return resourceDto;
    }

    public Resource fromDto(final ResourceDto resource){
        Resource resource1 = new Resource();
        resource1.setId(resource.getId());
        resource1.setName(resource.getName());
        resource1.setDescription(resource.getDescription());
        resource1.setType(resource.getType());
        resource1.setActive(resource.isActive());
        resource1.setCalendar(calendarMapper.fromDto(resource.getCalendar()));
        return resource1;
    }

    public List<ResourceDto> toDtoList(List<Resource> resourceList){
        return resourceList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
