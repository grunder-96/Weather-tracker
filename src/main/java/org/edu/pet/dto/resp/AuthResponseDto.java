package org.edu.pet.dto.resp;

import java.time.LocalDateTime;

public record AuthResponseDto(String sessionId, LocalDateTime sessionExpirationTime) {

}