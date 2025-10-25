package com.marlonb.hr_middleware.test_data;

import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.model.dto.AdminUpdateDto;
import com.marlonb.hr_middleware.model.dto.LoginRequestDto;

import java.time.LocalDateTime;

public class Admin1 {

    // BASE VALUES
    private static final AdminAccount BASE_ADMIN;
    private static final Long ADMIN_ID = 2L;
    private static final String ADMIN_USERNAME = "Admin1";
    private static final String ADMIN_PASSWORD = "Dummy#07";
    private static final LocalDateTime ADMIN_CREATION = LocalDateTime.of
                                                        (2025, 10, 20, 2, 40);

    // UPDATED VALUES
    private static final String UPDATE_ADMIN_USERNAME = "admin@123";
    private static final String UPDATE_ADMIN_PASSWORD = "test@123";

    // INVALID VALUES
    private static final String INVALID_ADMIN_PASSWORD = "invalidPass";

    static {
        BASE_ADMIN = new AdminAccount();
        BASE_ADMIN.setId(ADMIN_ID);
        BASE_ADMIN.setUsername(ADMIN_USERNAME);
        BASE_ADMIN.setPassword(ADMIN_PASSWORD);
        BASE_ADMIN.setCreatedAt(ADMIN_CREATION);
    }

    public static AdminAccount sampleAdmin1Data () {
        return BASE_ADMIN;
    }

    public static AdminResponseDto sampleAdmin1Response () {

        return new AdminResponseDto(
                BASE_ADMIN.getId(),
                BASE_ADMIN.getUsername(),
                BASE_ADMIN.getCreatedAt()
        );
    }

    public static AdminRequestDto sampleAdmin1Request () {

        return new AdminRequestDto(
                ADMIN_USERNAME,
                ADMIN_PASSWORD
        );
    }

    public static AdminUpdateDto sampleAdmin1Update () {

        return new AdminUpdateDto(
                UPDATE_ADMIN_USERNAME,
                UPDATE_ADMIN_PASSWORD
        );
    }

    // LOGIN REQUEST
    public static LoginRequestDto sampleLoginRequest () {

        return new LoginRequestDto(
                sampleAdmin1Data().getUsername(),
                sampleAdmin1Data().getPassword()
        );
    }

    public static LoginRequestDto sampleInvalidLoginRequest () {

        return new LoginRequestDto(
                sampleAdmin1Data().getUsername(),
                INVALID_ADMIN_PASSWORD
        );
    }
}
