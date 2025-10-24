package com.marlonb.hr_middleware.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDto {

    @NotNull(message = "Username is required")
    @Size(min = 2, max = 12, message = "Username must be between 2 and 12 characters")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, " +
                  "one number, and one special character")
    private String password;
}
