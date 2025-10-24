package com.marlonb.hr_middleware.test_assertions;

import com.marlonb.hr_middleware.model.dto.AdminResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminAssertions {

    public static void assertAdminServiceReturnedExpectedResponse (AdminResponseDto actualResponse,
                                                                   AdminResponseDto expectedResponse) {

        assertThat(actualResponse).usingRecursiveAssertion().isEqualTo(expectedResponse);
    }

    public static void assertAdminServiceReturnedExpectedResponse (List<AdminResponseDto> actualResponse,
                                                                   List<AdminResponseDto> expectedResponse) {

        assertThat(actualResponse).usingRecursiveAssertion().isEqualTo(expectedResponse);
    }
}
