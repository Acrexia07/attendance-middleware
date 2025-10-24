package com.marlonb.hr_middleware.model.mapper;

import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.model.dto.AdminResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface AdminMapper {

    @Mapping(target = "id", ignore = true)
    AdminResponseDto toResponse (AdminAccount adminAccount);
}
