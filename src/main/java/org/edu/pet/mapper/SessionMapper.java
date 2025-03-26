package org.edu.pet.mapper;

import org.edu.pet.dto.SessionResponseDto;
import org.edu.pet.model.UserSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SessionMapper {

    @Mapping(target = "id", expression = "java(session.getId() != null ? session.getId().toString() : null)")
    SessionResponseDto toDto(UserSession session);
}