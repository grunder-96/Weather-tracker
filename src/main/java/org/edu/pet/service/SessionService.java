package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.resp.SessionResponseDto;
import org.edu.pet.mapper.SessionMapper;
import org.edu.pet.model.User;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionService {

    @Value("${custom.session.expiration.days}")
    private int daysUntilExpiration;

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    UserSession create(User user) {

        UserSession session = UserSession.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(daysUntilExpiration))
                .build();

        return sessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public Optional<SessionResponseDto> get(UUID sessionId) {

        Optional<UserSession> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional.map(sessionMapper::toDto);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean isSessionExpired(SessionResponseDto sessionResponseDto) {

        return LocalDateTime.now().isAfter(sessionResponseDto.expiresAt());
    }
}