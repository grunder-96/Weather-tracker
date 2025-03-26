package org.edu.pet.mapper;

import org.edu.pet.dto.SignUpRequestDto;
import org.edu.pet.dto.UserResponseDto;
import org.edu.pet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserResponseDto toUserResponseDto();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "pass", qualifiedByName = "hashPass")
    User fromSignUpRequestDto(SignUpRequestDto signUpRequestDto);

    @Named("hashPass")
    default String hashPass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
}