package com.marlonb.hr_middleware.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyResponse {
    SERVER_KEY_VALUE("server"),
    RESOURCE_KEY_VALUE("resource"),
    CREDENTIALS_KEY_VALUE("credentials");

    private final String keyValue;
}
