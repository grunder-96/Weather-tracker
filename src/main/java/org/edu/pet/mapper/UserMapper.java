package org.edu.pet.mapper;

import org.edu.pet.dto.CreateUserDto;
import org.edu.pet.dto.ReadUserDto;
import org.edu.pet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    ReadUserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "hashPass")
    User toEntity(CreateUserDto createUserDto);

    @Named("hashPass")
    default String hashPass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
}