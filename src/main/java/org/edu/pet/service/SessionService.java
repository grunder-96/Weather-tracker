package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.SessionCookieSettings;
import org.edu.pet.dto.resp.SessionResponseDto;
import org.edu.pet.exception.InvalidSessionIdException;
import org.edu.pet.mapper.SessionMapper;
import org.edu.pet.model.User;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    @Transactional
    UserSession createInternal(User user) {

        UserSession session = UserSession.builder()
                .user(user)
                .expiresAt(LocalDateTime.now()
                        .plusSeconds(SessionCookieSettings.LIFETIME_SECONDS))
                .build();

        return sessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public Optional<SessionResponseDto> get(String uuidAsString) {

        UUID sessionId = convertToUuid(uuidAsString);
        Optional<UserSession> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional.map(sessionMapper::toSessionResponseDto);
    }

    @Transactional
    public void remove(String uuidAsString) {

        UUID sessionId = convertToUuid(uuidAsString);
        sessionRepository.deleteById(sessionId);
    }

    @Scheduled(fixedDelayString = "${fixedDelay.milliseconds}",
            initialDelayString = "${initialDelay.milliseconds}")
    @Transactional
    public void removeExpiredSessions() {

        LocalDateTime now = LocalDateTime.now();
        sessionRepository.deleteExpiredSessions(now);
    }

    public boolean isSessionExpired(SessionResponseDto sessionResponseDto) {

        return LocalDateTime.now().isAfter(sessionResponseDto.expiresAt());
    }

    private UUID convertToUuid(String uuidAsString) {

        try {
            return UUID.fromString(uuidAsString);
        } catch (IllegalArgumentException e) {
            throw new InvalidSessionIdException();
        }
    }
}