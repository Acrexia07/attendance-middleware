package com.marlonb.hr_middleware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlonb.hr_middleware.exception.custom.ResourceNotFoundException;
import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.model.dto.AdminUpdateDto;
import com.marlonb.hr_middleware.security.AdminUserDetailsService;
import com.marlonb.hr_middleware.security.JwtService;
import com.marlonb.hr_middleware.service.AdminService;
import com.marlonb.hr_middleware.test_config.TestSecurityConfig;
import com.marlonb.hr_middleware.test_data.Admin1;
import com.marlonb.hr_middleware.test_data.Admin2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.marlonb.hr_middleware.exception.enums.ExceptionMessages.*;
import static com.marlonb.hr_middleware.message.ErrorMessages.*;
import static com.marlonb.hr_middleware.message.SuccessfulMessages.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminController.class)
@Import(TestSecurityConfig.class)
public class AdminControllerSliceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AdminUserDetailsService adminUserDetailsService;

    private AdminRequestDto testAdminRequest;
    private AdminResponseDto testAdminResponseAfterRequest;
    private AdminAccount testAdmin;
    private AdminUpdateDto testAdminUpdate;
    private AdminResponseDto testAdminResponseAfterUpdate;

    @BeforeEach
    void setup() {
        testAdminRequest = Admin1.sampleAdmin1Request();
        testAdminResponseAfterRequest = Admin1.sampleAdmin1Response();

        testAdmin = Admin1.sampleAdmin1Data();
        testAdminUpdate = Admin1.sampleAdmin1Update();
        testAdminResponseAfterUpdate = Admin1.sampleAdmin1ResponseAfterUpdate();
    }

    @Nested
    class PositiveTests {

        @Test
        @WithMockUser(username = "1")
        @DisplayName("Should pass response successfully when create admin has valid credentials")
        void shouldPassResponseSuccessfullyWhenCreateAdminHasValidCredentials () throws Exception {

            when(adminService.createAdmin(testAdminRequest))
                    .thenReturn(testAdminResponseAfterRequest);

            String jsonAdminRequest = mapper.writeValueAsString(testAdminRequest);

            mockMvc.perform(post("/admins/register")
                            .with(csrf())
                            .content(jsonAdminRequest)
                            .contentType(MediaType.APPLICATION_JSON))
                   .andExpectAll(
                           status().isCreated(),
                           header().exists("Location"),
                           header().string("Location", containsString("/admins/register/")),
                           jsonPath("$.message").value(CREATE_SUCCESS_MESSAGE.getMessage()),
                           jsonPath("$.data.username").value(testAdminRequest.getUsername())
                   );
        }

        @Test
        @WithMockUser(username = "1")
        @DisplayName("Should pass response successfully when retrieve all admin accounts")
        void shouldPassResponseSuccessfullyWhenRetrieveAllAdminAccounts () throws Exception {

            AdminResponseDto testAdmin1Response = Admin1.sampleAdmin1Response();
            AdminResponseDto testAdmin2Response = Admin2.sampleAdmin2Response();

            List<AdminResponseDto> listOfAdminAccounts =
                    List.of(testAdmin1Response, testAdmin2Response);

            when(adminService.retrieveAllAdminData())
                    .thenReturn(listOfAdminAccounts);

            String jsonAdminResponse = mapper.writeValueAsString(listOfAdminAccounts);

            mockMvc.perform(get("/admins")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonAdminResponse))
                   .andExpectAll(
                           status().isOk(),
                           jsonPath("$.message").value(READ_ALL_SUCCESS_MESSAGE.getMessage()),
                           jsonPath("$.data").isArray(),
                           jsonPath("$.data.length()").value(2)
            );
        }

        @Test
        @WithMockUser(username = "1")
        @DisplayName("Should pass response successfully when retrieve specific admin account")
        void shouldPassResponseSuccessfullyWhenRetrieveSpecificAdminAccount () throws Exception {

            final long testAdminId = testAdminResponseAfterRequest.id();

            when(adminService.retrieveSpecificAdmin(testAdminId))
                    .thenReturn(testAdminResponseAfterRequest);

            String jsonAdminResponse = mapper.writeValueAsString(testAdminResponseAfterRequest);

            mockMvc.perform(get("/admins/{id}", testAdminId)
                            .content(jsonAdminResponse)
                            .contentType(MediaType.APPLICATION_JSON))
                   .andExpectAll(
                           status().isOk(),
                           jsonPath("$.message").value(READ_SUCCESS_MESSAGE.getMessage()),
                           jsonPath("$.data.id").value(testAdminResponseAfterRequest.id()),
                           jsonPath("$.data.username").value(testAdminResponseAfterRequest.username()));
        }

        @Test
        @WithMockUser(username = "1")
        @DisplayName("Should pass response successfully when update specific admin account")
        void shouldPassResponseSuccessfullyWhenUpdateSpecificAdminAccount () throws Exception {

            when(adminService.updateAdmin(testAdmin.getId(), testAdminUpdate))
                    .thenReturn(testAdminResponseAfterUpdate);

            String jsonAdminUpdate = mapper.writeValueAsString(testAdminUpdate);

            mockMvc.perform(put("/admins/{id}", testAdminResponseAfterUpdate.id())
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonAdminUpdate))
                   .andExpectAll(
                           status().isOk(),
                           jsonPath("$.message").value(UPDATE_SUCCESS_MESSAGE.getMessage()));
        }
    }

    @Nested
    class NegativeTests {

        @Nested
        class CreateAdminValidationTests {

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when create admin request has invalid username")
            void shouldFailWhenCreateAdminRequestHasInvalidUsername() throws Exception {

                AdminRequestDto invalidRequest = Admin1.sampleRequestWithInvalidUsername();

                when(adminService.createAdmin(invalidRequest))
                        .thenReturn(testAdminResponseAfterRequest);

                String jsonInvalidRequest = mapper.writeValueAsString(invalidRequest);

                mockMvc.perform(post("/admins/register")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonInvalidRequest))
                        .andExpectAll(
                                status().isBadRequest(),
                                jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage()),
                                jsonPath("$.errors.username").isNotEmpty());
            }

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when create admin request has invalid password")
            void shouldFailWhenCreateAdminRequestHasInvalidPassword () throws Exception {

                AdminRequestDto invalidRequest = Admin1.sampleRequestWithInvalidPassword();

                when(adminService.createAdmin(invalidRequest))
                        .thenReturn(testAdminResponseAfterRequest);

                String jsonInvalidRequest = mapper.writeValueAsString(invalidRequest);

                mockMvc.perform(post("/admins/register")
                                .with(csrf())
                                .content(jsonInvalidRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpectAll(
                                status().isBadRequest(),
                                jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage()),
                                jsonPath("$.errors.password").isNotEmpty());
            }

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when create admin request has missing field")
            void shouldFailWhenCreateAdminRequestHasMissingField () throws Exception {

                AdminRequestDto invalidRequest = Admin1.sampleRequestWithMissingField();

                when(adminService.createAdmin(invalidRequest))
                        .thenReturn(testAdminResponseAfterRequest);

                String jsonInvalidRequest = mapper.writeValueAsString(invalidRequest);

                mockMvc.perform(post("/admins/register")
                                .with(csrf())
                                .content(jsonInvalidRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpectAll(
                                status().isBadRequest(),
                                jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage())

                        );
            }
        }

        @Nested
        class ReadAdminValidationTests {

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when read specific admin has id that does not exists")
            void shouldFailWhenReadSpecificAdminHasIdThatDoesNotExists () throws Exception {

                final long nonExistentId = 999L;
                final String RESOURCE_NOT_FOUND_MESSAGE =
                        String.format(RESOURCE_NOT_FOUND.getErrorMessage(), nonExistentId);

                when(adminService.retrieveSpecificAdmin(nonExistentId))
                        .thenThrow(new ResourceNotFoundException
                                    (RESOURCE_NOT_FOUND_MESSAGE));

                mockMvc.perform(get("/admins/{id}", nonExistentId))
                       .andExpectAll(
                               status().isNotFound(),
                               jsonPath("$.errors.resource").value(RESOURCE_NOT_FOUND_MESSAGE));
            }
        }

        @Nested
        class UpdateAdminValidationTests {

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when update admin has invalid username")
            void shouldFailWhenUpdateAdminHasInvalidUsername () throws Exception {

                final Long testAdminId = testAdmin.getId();
                AdminUpdateDto testInvalidUpdate = Admin1.sampleUpdateWithInvalidUsername();

                String jsonInvalidUpdate = mapper.writeValueAsString(testInvalidUpdate);

                mockMvc.perform(put("/admins/{id}", testAdminId)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonInvalidUpdate))
                        .andDo(print())
                       .andExpectAll(
                               status().isBadRequest(),
                               jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage()),
                               jsonPath("$.errors.username").isNotEmpty());
            }

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when update admin has invalid password")
            void shouldFailWhenUpdateAdminHasInvalidPassword () throws Exception {

                final Long testAdminId = testAdmin.getId();
                AdminUpdateDto invalidUpdate = Admin1.sampleUpdateWithInvalidPassword();

                String jsonInvalidRequest = mapper.writeValueAsString(invalidUpdate);

                mockMvc.perform(put("/admins/{id}", testAdminId)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonInvalidRequest))
                       .andExpectAll(
                               status().isBadRequest(),
                               jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage()),
                               jsonPath("$.errors.password").isNotEmpty());
            }

            @Test
            @WithMockUser(username = "1")
            @DisplayName("Should fail when update admin has missing field")
            void shouldFailWhenUpdateAdminHasMissingField () throws Exception {

                final Long testAdminId = testAdmin.getId();
                AdminUpdateDto invalidUpdate = Admin1.sampleUpdateWithMissingField();

                String jsonInvalidUpdate = mapper.writeValueAsString(invalidUpdate);

                mockMvc.perform(put("/admins/{id}", testAdminId)
                                .with(csrf())
                                .content(jsonInvalidUpdate)
                                .contentType(MediaType.APPLICATION_JSON))
                       .andExpectAll(
                               status().isBadRequest(),
                               jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage()));
            }
        }
    }
}
