package com.marlonb.hr_middleware.test_data;

import com.marlonb.hr_middleware.model.admin.AdminAccount;

import java.time.LocalDateTime;

public class Admin1 {

    private static final AdminAccount BASE_ADMIN;
    private static final Long ADMIN_ID = 2L;
    private static final String ADMIN_USERNAME = "Admin1";
    private static final String ADMIN_PASSWORD = "Dummy#07";
    private static final LocalDateTime ADMIN_CREATION = LocalDateTime.of
                                                        (2025, 10, 20, 2, 40);

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

}
