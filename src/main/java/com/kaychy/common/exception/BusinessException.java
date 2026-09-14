package com.kaychy.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Base type for expected, domain-level failures (as opposed to bugs).
 * Subclasses pick the HTTP status; GlobalExceptionHandler translates
 * any BusinessException into a response with that status automatically,
 * so new failure types don't require touching the handler.
 */
public abstract class BusinessException extends RuntimeException {

    private final HttpStatus status;

    protected BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
