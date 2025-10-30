package com.marlonb.hr_middleware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlonb.hr_middleware.model.dto.LoginRequestDto;
import com.marlonb.hr_middleware.security.AdminUserDetailsService;
import com.marlonb.hr_middleware.security.JwtService;
import com.marlonb.hr_middleware.service.AuthService;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static com.marlonb.hr_middleware.message.ErrorMessages.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class AuthControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AdminUserDetailsService adminUserDetailsService;

    private LoginRequestDto testLogin;
    private LoginRequestDto invalidTestLogin;

    @BeforeEach
    void initSetup () {
        testLogin = Admin1.sampleLoginRequest();
        invalidTestLogin = Admin1.sampleInvalidLoginRequest();
    }

    @Nested
    class PositiveTests {

        @Test
        @DisplayName("Should login successfully")
        void shouldLoginSuccessfully () throws Exception {

            String expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                                   ".eyJzdWIiOiJ0ZXN0VXNlciIsImlhdCI6MTcz" +
                                   "NTYwMzIwMCwiZXhwIjoxNzM1Njg5NjAwfQ.signature";

            when(authService.verifyUserCredentials(testLogin)).thenReturn(expectedToken);

            String jsonValidCredentials = mapper.writeValueAsString(testLogin);

            mockMvc.perform(post("/auth/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonValidCredentials))
                    .andDo(print())
                   .andExpectAll(
                            status().isOk(),
                            jsonPath("$.message").value("Admin login successfully"),
                            jsonPath("$.token").value(expectedToken));
        }

    }

    @Nested
    class NegativeTests {

        @Test
        @DisplayName("Should fail to login when admin has invalid credentials")
        void shouldThrowAnErrorOnInvalidCredentials () throws Exception {

            when(authService.verifyUserCredentials(invalidTestLogin))
                    .thenThrow(new BadCredentialsException(INVALID_CREDENTIALS_FOUND.getErrorMessage()));

            String jsonInvalidCredentials = mapper.writeValueAsString(invalidTestLogin);

            mockMvc.perform(post("/auth/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonInvalidCredentials))
                   .andExpectAll(
                           status().isUnauthorized());
        }
    }
}
