package com.kaychy.common.exception;

import org.springframework.http.HttpStatus;

/** Thrown for input that fails a business rule bean validation can't express. */
public class InvalidRequestException extends BusinessException {

    public InvalidRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
