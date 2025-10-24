package com.marlonb.hr_middleware.controller;

import com.marlonb.hr_middleware.model.dto.LoginRequestDto;
import com.marlonb.hr_middleware.service.authService;
import com.marlonb.hr_middleware.utils.ResponseMessageDto;
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
@RequestMapping("batch/v1")
public class AuthController {

    private final authService authService;

    @PostMapping("/admin/login")
    public ResponseEntity<ResponseMessageDto<String>> loginAdmin (@Valid @RequestBody
                                                                      LoginRequestDto loginRequest) {

        String outputResponse = authService.verifyUserCredentials(loginRequest);

        return ResponseEntity.ok().body(new ResponseMessageDto<>(
                                            LOGIN_SUCCESS_MESSAGE.getMessage(),
                                            outputResponse));
    }

}
