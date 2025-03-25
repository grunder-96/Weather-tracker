package org.edu.pet.dto;

import java.time.LocalDateTime;

public record SessionResponseDto(String id, LocalDateTime expiresAt) {

}