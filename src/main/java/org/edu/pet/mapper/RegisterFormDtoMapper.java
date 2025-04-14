package org.edu.pet.mapper;

import org.edu.pet.dto.req.CreateUserDto;
import org.edu.pet.dto.req.RegisterFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RegisterFormDtoMapper {

    @Mapping(source = "pass", target = "password")
    CreateUserDto toDto(RegisterFormDto registerFormDto);
}
