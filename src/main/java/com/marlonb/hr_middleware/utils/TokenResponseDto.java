package com.marlonb.hr_middleware.utils;

public record TokenResponseDto<T>(
        String message,
        T token
) {
}
