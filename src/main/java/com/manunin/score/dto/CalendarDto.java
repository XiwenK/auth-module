package com.manunin.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarDto {

    @Schema(description = "Calendar id", example = "1")
    private long id;

    @Schema(description = "Calendar name", example = "Calendar 1")
    private String name;

    @Schema(description = "Calendar description", example = "Calendar 1 description")
    private String description;

    @Schema(description = "Calendar data", example = "{\"key\":\"value\"}")
    private JsonNode data;

    private boolean isSystem;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
