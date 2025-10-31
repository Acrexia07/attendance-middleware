package com.marlonb.hr_middleware.controller;

import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.service.AdminService;
import com.marlonb.hr_middleware.utils.response.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.marlonb.hr_middleware.message.SuccessfulMessages.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<AdminResponseDto>> createAdmin (@Valid @RequestBody
                                                                         AdminRequestDto adminRequest) {

        AdminResponseDto response = adminService.createAdmin(adminRequest);
        URI location = URI.create("/admins/register/" + response.id());

        return ResponseEntity.created(location)
                             .body(new ApiResponseDto<>
                                     (CREATE_SUCCESS_MESSAGE.getMessage(),
                                      response));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<AdminResponseDto>>> retrieveAllAdmins () {

        List<AdminResponseDto> response = adminService.retrieveAllAdminData();

        return ResponseEntity.ok().body(new ApiResponseDto<>
                                        (READ_ALL_SUCCESS_MESSAGE.getMessage(),
                                         response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AdminResponseDto>> retrieveAdmin (@PathVariable Long id) {

        AdminResponseDto response = adminService.retrieveSpecificAdmin(id);

        return ResponseEntity.ok().body(new ApiResponseDto<>
                                        (READ_SUCCESS_MESSAGE.getMessage(),
                                         response));
    }
}
