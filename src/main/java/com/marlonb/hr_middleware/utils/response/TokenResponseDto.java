package com.marlonb.hr_middleware.utils.response;

public record TokenResponseDto<T>(
        String message,
        T token
) {
}
