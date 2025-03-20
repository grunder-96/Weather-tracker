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

    @Transactional
    public Optional<UserSession> getSession(UUID sessionId) {
        return sessionRepository.getSession(sessionId);
    }

    public boolean isSessionExpired(UserSession session) {
        return LocalDateTime.now().isAfter(session.getExpiresAt());
    }
}