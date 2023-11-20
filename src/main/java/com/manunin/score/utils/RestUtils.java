package com.manunin.score.utils;

import com.manunin.score.dto.MessageResponse;
import com.manunin.score.exception.ResultType;
import org.springframework.http.ResponseEntity;

public class RestUtils {
    public static ResponseEntity<MessageResponse> response(ResultType resultType) {
        return ResponseEntity.status(resultType.getStatus()).body(new MessageResponse(resultType.getMessage()));
    }
}
