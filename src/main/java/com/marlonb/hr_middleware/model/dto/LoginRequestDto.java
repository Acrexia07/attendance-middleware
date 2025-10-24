package com.marlonb.hr_middleware.model.dto;

public record LoginRequestDto(
        String username,
        String password
) {}
