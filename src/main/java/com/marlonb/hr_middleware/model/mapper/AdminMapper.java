package com.marlonb.hr_middleware.model.mapper;

import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminRequestDto;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import com.marlonb.hr_middleware.model.dto.AdminUpdateDto;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface AdminMapper {

    AdminResponseDto toResponse (AdminAccount adminAccount);

    @Mapping(target = "id", ignore = true)
    AdminAccount toEntity (AdminRequestDto adminRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateFromEntity (@MappingTarget AdminAccount admin, AdminUpdateDto adminUpdate);
}
