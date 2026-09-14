package com.kaychy.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when a request conflicts with existing state — e.g. registering
 * with an email that's already taken.
 */
public class ResourceConflictException extends BusinessException {

    public ResourceConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
