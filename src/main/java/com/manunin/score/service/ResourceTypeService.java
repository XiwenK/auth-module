package com.manunin.score.service;

import com.manunin.score.dto.ResourceTypeDto;
import com.manunin.score.model.ResourceType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceTypeService {
    public List<ResourceTypeDto> getResourceTypes() {
        return Arrays.stream(ResourceType.values()).map(resourceType -> {
            ResourceTypeDto resourceTypeDto = new ResourceTypeDto();
            resourceTypeDto.setId(resourceType.name());
            resourceTypeDto.setName(resourceType.getName());
            return resourceTypeDto;
        }).collect(Collectors.toList());
    }
}
