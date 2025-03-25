package org.edu.pet.mapper;

import org.edu.pet.dto.SessionResponseDto;
import org.edu.pet.model.UserSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "id", expression = "java(session.getId() != null ? session.getId().toString() : null)")
    SessionResponseDto toSessionResponseDto(UserSession session);
}