package org.edu.pet.repository;

import lombok.RequiredArgsConstructor;
import org.edu.pet.model.UserSession;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final SessionFactory sessionFactory;

    public Optional<UserSession> getSession(UUID sessionId) {
        var session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(UserSession.class, sessionId));
    }
}