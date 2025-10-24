package com.marlonb.hr_middleware.exception.custom;

public class DuplicateResourceFoundException extends RuntimeException {

    public DuplicateResourceFoundException(String message) {
        super(message);
    }
}
