package com.marlonb.hr_middleware.model.dto;

import java.time.LocalDateTime;

public record AdminResponseDto(
        Long id,
        String username,
        LocalDateTime createdAt) {}
