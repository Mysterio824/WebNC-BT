package com.webnc.bt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum     ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    ACTOR_NOT_FOUND(1001, "Actor not found", HttpStatus.NOT_FOUND),
    FILM_NOT_FOUND(1002, "Film not found", HttpStatus.NOT_FOUND),
    LANGUAGE_NOT_FOUND(1003, "Language not found", HttpStatus.NOT_FOUND)
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}