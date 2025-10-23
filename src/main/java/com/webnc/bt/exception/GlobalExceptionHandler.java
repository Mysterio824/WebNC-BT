package com.webnc.bt.exception;

import com.webnc.bt.dto.response.AppApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<AppApiResponse<String>> handlingException(Exception exception) {
        AppApiResponse<String> appApiResponse = new AppApiResponse<String>();
//        appApiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        appApiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        appApiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(appApiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<AppApiResponse<String>> handlingAppException(AppException exception) {
        AppApiResponse<String> appApiResponse = new AppApiResponse<String>();
        ErrorCode errorCode = exception.getErrorCode();
        appApiResponse.setCode(errorCode.getCode());
        appApiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(appApiResponse);
    }
}