package com.manunin.score.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupDto {

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "User name")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Schema(description = "User email")
    private String email;

    @Schema(description = "User roles")
    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "User password")
    private String password;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "User first name")
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "User last name")
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
