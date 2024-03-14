package com.manunin.score.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class ErrorResponse {
    @NotBlank
    @Schema(description = "Error message", example = "Error message")
    private final String message;

    @NotBlank
    @Schema(description = "Error code: " +
            "1 - General error (HTTP: 500 - Internal Server Error), " +
            "2 - Authentication failed (HTTP: 401 - Unauthorized), " +
            "3 - JWT token expired (HTTP: 401 - Unauthorized)",
            example = "2")
    private final ErrorCode code;

    @NotBlank
    @Schema(description = "Error code", example = "401")
    private final HttpStatus status;

    @NotBlank
    @Schema(description = "Error timestamp", example = "2021-08-25T15:00:00")
    private final Date timestamp;

    public ErrorResponse(String message, ErrorCode code, HttpStatus status, Date timestamp) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.timestamp = timestamp;
    }

    public static ErrorResponse of(String message, ErrorCode code, HttpStatus status) {
        return new ErrorResponse(message, code, status, new Date());
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
