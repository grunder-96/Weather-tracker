package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    private final SessionMapper sessionMapper;

    @Transactional
    public Optional<UserSession> getSession(UUID sessionId) {
        return sessionRepository.getSession(sessionId);
    }

    @Transactional(readOnly = true)
    public Optional<SessionResponseDto> get(UUID sessionId) {
        Optional<UserSession> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional.map(sessionMapper::toSessionResponseDto);
    }

    public boolean isSessionExpired(SessionResponseDto sessionResponseDto) {
        return LocalDateTime.now().isAfter(sessionResponseDto.expiresAt());
    }
}