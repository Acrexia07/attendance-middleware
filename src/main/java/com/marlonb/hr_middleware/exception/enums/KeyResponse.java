package com.marlonb.hr_middleware.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyResponse {
    SERVER_KEY_VALUE("server");

    private final String keyValue;
}
