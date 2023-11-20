package com.manunin.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDto {

    @Schema(example = "1", description = "Student id")
    private Long id;

    @NotBlank
    @Schema(example = "Ivan", description = "Student name")
    private String name;

    @NotNull
    @Schema(example = "123456789", description = "Student phone")
    private String phone;


    @Schema(example = "Comment", description = "Comment")
    private String comment;

    private int amount;

    @NotNull
    @Schema(description = "Group")
    private Long groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroup(Long groupID) {
        this.groupId = groupId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
