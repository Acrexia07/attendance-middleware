package com.marlonb.hr_middleware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.security.AdminUserDetailsService;
import com.marlonb.hr_middleware.security.JwtService;
import com.marlonb.hr_middleware.service.AdminService;
import com.marlonb.hr_middleware.test_config.TestSecurityConfig;
import com.marlonb.hr_middleware.test_data.Admin1;
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

import static com.marlonb.hr_middleware.exception.enums.ExceptionMessages.*;
import static com.marlonb.hr_middleware.message.SuccessfulMessages.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @BeforeEach
    void setup() {
        testAdminRequest = Admin1.sampleAdmin1Request();
        testAdminResponseAfterRequest = Admin1.sampleAdmin1Response();
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

            mockMvc.perform(post("/admin/register")
                            .with(csrf())
                            .content(jsonAdminRequest)
                            .contentType(MediaType.APPLICATION_JSON))
                   .andExpectAll(
                           status().isCreated(),
                           header().exists("Location"),
                           header().string("Location", containsString("/admin/register/")),
                           jsonPath("$.message").value(CREATE_SUCCESS_MESSAGE.getMessage()),
                           jsonPath("$.data.username").value(testAdminRequest.getUsername())
                   );
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

                mockMvc.perform(post("/admin/register")
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

                mockMvc.perform(post("/admin/register")
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

                mockMvc.perform(post("/admin/register")
                                .with(csrf())
                                .content(jsonInvalidRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpectAll(
                                status().isBadRequest(),
                                jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getErrorMessage())

                        );
            }
        }
    }
}
