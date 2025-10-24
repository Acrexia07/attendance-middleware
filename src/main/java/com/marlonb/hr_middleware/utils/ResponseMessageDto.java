package com.marlonb.hr_middleware.utils;

public record ResponseMessageDto<T> (
        String message,
        T response
) {}
