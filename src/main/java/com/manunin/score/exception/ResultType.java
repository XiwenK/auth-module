package com.manunin.score.exception;

import org.springframework.http.HttpStatus;

public enum ResultType {
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "message"),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "exceptions.usernameAlreadyExists"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "exceptions.emailAlreadyExists"),
    USER_SUCCESSFULLY_CREATED(HttpStatus.OK.value(), "registerPage.successMessage"),

    USER_SUCCESSFULLY_MODIFIED(HttpStatus.OK.value(), "user.successfullyModified"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "message"),

    GROUP_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "exceptions.groupNameAlreadyExists"),

    GROUP_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "group.exceptions.groupNotFound"),
    GROUP_SUCCESSFULLY_CREATED(HttpStatus.OK.value(), "group.successfullyCreated"),

    STUDENT_WITH_PHONE_AND_NUMBER_ALREADY_ADDED(HttpStatus.BAD_REQUEST.value(), "studentsList.exceptions.studentWithPhoneAndNumberAlreadyAdded"),
    GROUP_STUDENT_SUCCESSFULLY_CREATED(HttpStatus.OK.value(), "studentsList.successfullyCreated"),
    GROUP_STUDENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "studentsList.exceptions.groupStudentNotFound"),
    STUDENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "studentsList.exceptions.studentNotFound"),
    RESOURCE_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "exceptions.resourceNameAlreadyExists"),
    RESOURCE_SUCCESSFULLY_CREATED(HttpStatus.OK.value(), "resource.successfullyCreated");

    private final int status;
    private final String message;

    ResultType(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
