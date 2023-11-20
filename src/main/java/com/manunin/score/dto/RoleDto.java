package com.manunin.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    @Schema(example = "1", description = "Role id")
    private long id;

    @NotBlank
    @Schema(example = "Analyst", description = "Role name", required = true)
    private String name;

    @Schema(example = "true", description = "System flag, says if the role is system")
    private boolean system;

    @Schema(example = "true", description = "Active flag, says if the role is active")
    private boolean active;

    @NotEmpty
    @Schema(description = "Roles privileges")
    private List<PrivilegeDto> privileges;

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

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<PrivilegeDto> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeDto> privileges) {
        this.privileges = privileges;
    }
}
