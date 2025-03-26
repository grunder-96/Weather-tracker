package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.SessionResponseDto;
import org.edu.pet.mapper.SessionMapper;
import org.edu.pet.model.User;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.edu.pet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    public static final int DAYS_UNTIL_EXPIRATION = 30;

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    private final SessionMapper sessionMapper;

    @Transactional
    public SessionResponseDto create(Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        UserSession session = UserSession.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(DAYS_UNTIL_EXPIRATION))
                .build();

        return sessionMapper.toDto(sessionRepository.save(session));
    }

    @Transactional(readOnly = true)
    public Optional<SessionResponseDto> get(UUID sessionId) {
        Optional<UserSession> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional.map(sessionMapper::toDto);
    }

    public boolean isSessionExpired(SessionResponseDto sessionResponseDto) {
        return LocalDateTime.now().isAfter(sessionResponseDto.expiresAt());
    }
}