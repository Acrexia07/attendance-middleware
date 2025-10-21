package com.marlonb.hr_middleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    USER_NOT_FOUND("User not found");
    private final String errorMessage;
}
