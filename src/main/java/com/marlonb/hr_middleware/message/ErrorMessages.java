package com.marlonb.hr_middleware.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    USER_NOT_FOUND("User not found"),
    INVALID_CREDENTIALS_FOUND("Invalid username or password"),
    DUPLICATE_RESOURCE_FOUND("This username %s already exists"),
    RESOURCE_NOT_FOUND("This admin id '%d' does not exist");
    private final String errorMessage;
}
