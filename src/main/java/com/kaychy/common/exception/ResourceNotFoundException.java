package com.kaychy.common.exception;

import org.springframework.http.HttpStatus;

/** Thrown when a referenced resource (by ID) doesn't exist. */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}