package com.marlonb.hr_middleware.controller;

import com.marlonb.hr_middleware.model.dto.LoginRequestDto;
import com.marlonb.hr_middleware.service.AuthService;
import com.marlonb.hr_middleware.utils.TokenResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.marlonb.hr_middleware.message.SuccessfulMessages.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponseDto<String>> loginAdmin (@Valid @RequestBody
                                                                      LoginRequestDto loginRequest) {

        String outputResponse = authService.verifyUserCredentials(loginRequest);

        return ResponseEntity.ok().body(new TokenResponseDto<>(
                                            LOGIN_SUCCESS_MESSAGE.getMessage(),
                                            outputResponse));
    }

}
