package com.manunin.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceDto {
    @Schema(example = "1", description = "Space id", required = true)
    private long id;

    @NotBlank
    @Schema(example = "Space one", description = "Space name", required = true)
    private String name;

    @NotBlank
    @Schema(example = "The best space", description = "Description of the space", required = true)
    private String description;

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
}
