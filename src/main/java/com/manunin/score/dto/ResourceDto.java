package com.manunin.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manunin.score.model.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDto {

    @Schema(example = "1", description = "Resource id")
    private long id;

    @Schema(example = "Analyst", description = "Resource name")
    private String name;

    @Schema(example = "Analyst", description = "Resource description", maxLength = 255)
    private String description;

    @Schema(example = "2", description = "Resource type")
    private ResourceType type;

    @Schema(example = "1", description = "Resource calendar DTO")
    private CalendarDto calendar;

    @Schema(example = "false", description = "Active flag")
    private boolean isActive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public CalendarDto getCalendar() {
        return calendar;
    }

    public void setCalendar(CalendarDto calendarDto) {
        this.calendar = calendarDto;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
