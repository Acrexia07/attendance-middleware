package com.marlonb.hr_middleware.service;

import com.marlonb.hr_middleware.exception.custom.DuplicateResourceFoundException;
import com.marlonb.hr_middleware.exception.custom.ResourceNotFoundException;
import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.model.dto.AdminUpdateDto;
import com.marlonb.hr_middleware.model.mapper.AdminMapper;
import com.marlonb.hr_middleware.repository.AdminRepository;
import com.marlonb.hr_middleware.test_data.Admin1;
import com.marlonb.hr_middleware.test_data.Admin2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.marlonb.hr_middleware.test_assertions.AdminAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AdminServiceUnitTests {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminService adminService;

    private AdminAccount testAdmin;
    private AdminRequestDto testAdminRequest;
    private AdminResponseDto testAdminResponse;
    private long testAdminId;
    private AdminUpdateDto testAdminUpdate;
    private AdminAccount testAdminAfterUpdate;
    private AdminResponseDto testAdminResponseAfterUpdate;

    @BeforeEach
    void initSetup () {
        testAdmin = Admin1.sampleAdmin1Data();
        testAdminId = testAdmin.getId();
        testAdminRequest = Admin1.sampleAdmin1Request();
        testAdminResponse = Admin1.sampleAdmin1Response();

        testAdminUpdate = Admin1.sampleAdmin1Update();
        testAdminAfterUpdate = Admin1.sampleAdmin1AfterUpdate();
        testAdminResponseAfterUpdate = Admin1.sampleAdmin1ResponseAfterUpdate();
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
                    .thenReturn(testAdminResponse);

            AdminResponseDto actualResponse = adminService.createAdmin(testAdminRequest);

            assertAdminServiceReturnedExpectedResponse(actualResponse, testAdminResponse);
        }

        @Test
        @DisplayName("Should retrieve all admin data successfully")
        void shouldRetrieveAllAdminDataSuccessfully () {

            AdminAccount testAdmin2 = Admin2.sampleAdmin2Data();
            AdminResponseDto testAdminResponse2 = Admin2.sampleAdmin2Response();

            List<AdminAccount> listOfAdminData =
                    List.of(testAdmin, testAdmin2);
            List<AdminResponseDto> listOfAdminResponses =
                    List.of(testAdminResponse, testAdminResponse2);

            when(adminRepository.findAll())
                    .thenReturn(listOfAdminData);

            when(adminMapper.toResponse(testAdmin))
                    .thenReturn(testAdminResponse);

            when(adminMapper.toResponse(testAdmin2))
                    .thenReturn(testAdminResponse2);

            List<AdminResponseDto> actualResponse = adminService.retrieveAllAdminData();

            assertAdminServiceReturnedExpectedResponse(actualResponse, listOfAdminResponses);
        }

        @Test
        @DisplayName("Should retrieve specific admin data successfully")
        void shouldRetrieveSpecificAdminDataSuccessfully () {

            when(adminRepository.findById(testAdminId))
                    .thenReturn(Optional.of(testAdmin));

            when(adminMapper.toResponse(testAdmin))
                    .thenReturn(testAdminResponse);

            AdminResponseDto actualResponse = adminService.retrieveSpecificAdmin(testAdminId);

            assertAdminServiceReturnedExpectedResponse(actualResponse, testAdminResponse);
        }

        @Test
        @DisplayName("Should update specific admin data successfully")
        void shouldUpdateSpecificAdminDataSuccessfully () {

            when(adminRepository.findById(testAdminId))
                    .thenReturn(Optional.of(testAdmin));

            doNothing().when(adminMapper).toUpdateFromEntity(testAdmin, testAdminUpdate);

            when(adminRepository.save(testAdmin))
                    .thenReturn(testAdminAfterUpdate);

            when(adminMapper.toResponse(testAdminAfterUpdate))
                    .thenReturn(testAdminResponseAfterUpdate);

            AdminResponseDto actualResponse = adminService.updateAdmin(testAdminId, testAdminUpdate);

            assertAdminServiceReturnedExpectedResponse(actualResponse, testAdminResponseAfterUpdate);
        }
    }

    @Nested
    class NegativeTests {

        @Test
        @DisplayName("Should fail to create when username already exists")
        void shouldFailToCreateWhenUsernameAlreadyExists () {

            when(adminMapper.toEntity(any(AdminRequestDto.class)))
                    .thenReturn(testAdmin);

            when(adminRepository.existsByUsername(anyString()))
                    .thenReturn(true);

            assertThrows(DuplicateResourceFoundException.class,
                    () -> adminService.createAdmin(testAdminRequest));
        }

        @Test
        @DisplayName("Should fail to read specific admin if id not exists")
        void shouldFailToReadSpecificAdminIfIdNotExists () {

            final long nonExistentId = 999L;

            when(adminRepository.findById(nonExistentId))
                    .thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class,
                         () -> adminService.retrieveSpecificAdmin(nonExistentId));
        }

        @Test
        @DisplayName("Should fail to update when username already exists")
        void shouldFailToUpdateWhenUsernameAlreadyExists () {

            when(adminRepository.findById(testAdminId))
                    .thenReturn(Optional.of(testAdmin));

            when(adminRepository.existsByUsername(testAdminUpdate.getUsername()))
                    .thenReturn(true);

            assertThrows(DuplicateResourceFoundException.class,
                    () -> adminService.updateAdmin(testAdminId, testAdminUpdate));
        }
    }
}
