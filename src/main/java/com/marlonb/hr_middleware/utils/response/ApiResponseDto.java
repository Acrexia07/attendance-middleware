package com.marlonb.hr_middleware.utils.response;

public record ApiResponseDto<T> (
        String message,
        T data
) {}
