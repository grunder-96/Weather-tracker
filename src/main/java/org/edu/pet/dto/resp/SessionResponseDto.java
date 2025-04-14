package org.edu.pet.dto.resp;

import java.time.LocalDateTime;

public record SessionResponseDto(String id, ReadUserDto user, LocalDateTime expiresAt) {

}