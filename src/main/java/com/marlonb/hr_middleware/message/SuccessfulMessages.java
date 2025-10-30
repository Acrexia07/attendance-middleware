package com.marlonb.hr_middleware.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessfulMessages {

    LOGIN_SUCCESS_MESSAGE("Admin login successfully"),
    CREATE_SUCCESS_MESSAGE("Admin created successfully");

    private final String message;
}
