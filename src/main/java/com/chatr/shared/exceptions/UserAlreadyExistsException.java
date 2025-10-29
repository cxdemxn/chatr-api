package com.chatr.shared.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String message) {
        super(message, "USER_ALREADY_EXISTS", HttpStatus.NOT_FOUND);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, "USER_ALREADY_EXISTS", HttpStatus.NOT_FOUND, cause);
    }
}