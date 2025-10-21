package com.marlonb.hr_middleware.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ErrorResponseDto(
    LocalDateTime timestamp,
    int statusCode,
    String message,
    Map<String, List<String>> errors,
    String path
) {}
