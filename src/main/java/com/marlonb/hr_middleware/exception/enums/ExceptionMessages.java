package com.marlonb.hr_middleware.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {
    INTERNAL_SERVER_ERROR_MESSAGE("Internal server error");
    private final String errorMessage;
}
