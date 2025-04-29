package org.edu.pet.mapper;

import org.edu.pet.dto.req.CreateUserDto;
import org.edu.pet.dto.req.RegisterFormDto;
import org.edu.pet.dto.resp.ReadUserDto;
import org.edu.pet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserDtoMapper {

    ReadUserDto toReadUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "hashPass")
    User toEntity(CreateUserDto createUserDto);

    @Mapping(target = "password", ignore = true)
    User toEntity(ReadUserDto readUserDto);

    @Mapping(source = "pass", target = "password")
    CreateUserDto toCreateUserDto(RegisterFormDto registerFormDto);

    @Named("hashPass")
    default String hashPass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
}