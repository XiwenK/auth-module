package com.manunin.score.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserDto {

    @Schema(description = "User id", example = "1")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "User name")
    private String username;

    @Email
    @Schema(description = "User email")
    private String email;

    @Size(min = 8, max = 20)
    @Schema(description = "User password")
    private String password;

    @Size(max = 50)
    @Schema(description = "User first name")
    private String firstName;

    @Size(max = 50)
    @Schema(description = "User last name")
    private String lastName;

    @Schema(description = "User roles")
    private Set<RoleDto> roles;

    @Schema(description = "User spaces")
    private Set<SpaceDto> spaces;

    @Schema(description = "User roles")
    private boolean isLocked;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String password, String firstName, String lastName,
                   Set<RoleDto> roles, Set<SpaceDto> spaces, boolean isLocked) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.spaces = spaces;
        this.isLocked = isLocked;
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

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean locked) {
        isLocked = locked;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public void setSpaces(Set<SpaceDto> spaces) {
        this.spaces = spaces;
    }
}
