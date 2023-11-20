package com.manunin.score.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserDto {

    @Schema(description = "User id")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "User name")
    private String username;

    @Size(max = 50)
    @Email
    @Schema(description = "User email")
    private String email;

    @Size(min = 6, max = 40)
    @Schema(description = "User password")
    private String password;

    @Schema(description = "User roles")
    private Set<RoleDto> roles;

    @Schema(description = "User spaces")
    private Set<SpaceDto> spaces;

    @Schema(description = "User roles")
    private boolean isLocked;

    public UserDto(Long id, String username, String email, Set<RoleDto> roles, Set<SpaceDto> spaces) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.spaces = spaces;
    }
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public Set<SpaceDto> getSpaces() {
        return spaces;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
