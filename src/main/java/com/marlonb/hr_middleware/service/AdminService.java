package com.marlonb.hr_middleware.service;


import com.marlonb.hr_middleware.exception.custom.DuplicateResourceFoundException;
import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.model.mapper.AdminMapper;
import com.marlonb.hr_middleware.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import static com.marlonb.hr_middleware.message.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // PROCESS(CREATE): Create new admin credentials and pass it as response
    @Transactional
    public AdminResponseDto createAdmin (@Valid @RequestBody AdminRequestDto adminRequest) {

        AdminAccount createdAdmin = adminMapper.toEntity(adminRequest);

        // Validation: Check if requested credentials is already exists in the database
        if (adminRepository.existsByUsername(createdAdmin.getUsername())) {
            throw new DuplicateResourceFoundException(String.format(DUPLICATE_RESOURCE_FOUND.getErrorMessage(),
                                                                    createdAdmin.getUsername()));
        }

        AdminAccount savedRepository = adminRepository.save(createdAdmin);

        return adminMapper.toResponse(savedRepository);
    }
}
