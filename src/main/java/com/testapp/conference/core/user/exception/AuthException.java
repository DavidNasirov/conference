package com.testapp.conference.core.user.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public AuthException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
