package com.marlonb.hr_middleware.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {
    INTERNAL_SERVER_ERROR_MESSAGE("Internal server error"),
    DUPLICATE_RESOURCE_ERROR_MESSAGE("Resource Duplication found"),
    UNAUTHORIZED_ERROR_MESSAGE("Unauthorized credentials"),
    VALIDATION_ERROR_MESSAGE("Validation error"),
    RESOURCE_NOT_FOUND_MESSAGE("Data not found");
    private final String errorMessage;
}
