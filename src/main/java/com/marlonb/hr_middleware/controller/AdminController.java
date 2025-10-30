package com.marlonb.hr_middleware.controller;

import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.service.AdminService;
import com.marlonb.hr_middleware.utils.response.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static com.marlonb.hr_middleware.message.SuccessfulMessages.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<AdminResponseDto>> createAdmin (@Valid @RequestBody
                                                                         AdminRequestDto adminRequest) {

        AdminResponseDto response = adminService.createAdmin(adminRequest);
        URI location = URI.create("/admin/register/" + response.id());

        return ResponseEntity.created(location)
                             .body(new ApiResponseDto<>
                                     (CREATE_SUCCESS_MESSAGE.getMessage(),
                                      response));
    }
}
