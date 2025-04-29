package org.edu.pet.mapper;

import org.edu.pet.dto.resp.AuthResponseDto;
import org.edu.pet.dto.resp.SessionResponseDto;
import org.edu.pet.model.UserSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SessionMapper {

    SessionResponseDto toSessionResponseDto(UserSession session);

    @Mapping(target = "sessionId", expression = "java(session.getId() != null ? session.getId().toString() : null)")
    @Mapping(target = "sessionExpirationTime", source = "expiresAt")
    AuthResponseDto toAuthResponseDto(UserSession session);
}