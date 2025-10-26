package com.marlonb.hr_middleware.service;

import com.marlonb.hr_middleware.exception.custom.DuplicateResourceFoundException;
import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.model.dto.AdminUpdateDto;
import com.marlonb.hr_middleware.model.mapper.AdminMapper;
import com.marlonb.hr_middleware.repository.AdminRepository;
import com.marlonb.hr_middleware.test_data.Admin1;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.marlonb.hr_middleware.test_assertions.AdminAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthServiceUnitTests {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminService adminService;

    private AdminAccount testAdmin;
    private AdminResponseDto expectedResponse;
    private AdminRequestDto testAdminRequest;
    private AdminUpdateDto testAdminUpdate;

    @BeforeEach
    void initialSetup () {
        testAdmin = Admin1.sampleAdmin1Data();
        testAdminRequest = Admin1.sampleAdmin1Request();
        testAdminUpdate = Admin1.sampleAdmin1Update();
        expectedResponse = Admin1.sampleAdmin1Response();
    }

    @Nested
    class PositiveTests {

        @Test
        @DisplayName("Should create admin successfully")
        void shouldCreateAdminSuccessfully () {

            when(adminMapper.toEntity(any(AdminRequestDto.class)))
                    .thenReturn(testAdmin);

            when(adminRepository.save(any(AdminAccount.class)))
                    .thenReturn(testAdmin);

            when(adminMapper.toResponse(any(AdminAccount.class)))
                    .thenReturn(expectedResponse);

            AdminResponseDto actualResponse = adminService.createAdmin(testAdminRequest);

            assertAdminServiceReturnedExpectedResponse(actualResponse, expectedResponse);
        }
    }

    @Nested
    class NegativeTests {

        @Test
        @DisplayName("Should fail if username already exists")
        void shouldFailIfUsernameAlreadyExists () {

            when(adminMapper.toEntity(any(AdminRequestDto.class)))
                    .thenReturn(testAdmin);

            when(adminRepository.existsByUsername("Admin1"))
                    .thenReturn(true);

            assertThrows(DuplicateResourceFoundException.class,
                    () -> adminService.createAdmin(testAdminRequest));
        }
    }
}
