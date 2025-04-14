package org.edu.pet.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.resp.SessionResponseDto;
import org.edu.pet.exception.InvalidSessionIdException;
import org.edu.pet.exception.SessionNotFoundOrExpiredException;
import org.edu.pet.service.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.util.UUID;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${custom.session.cookie.name}")
    private String customSessionCookieName;

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Cookie cookie = WebUtils.getCookie(req, customSessionCookieName);

        if (cookie == null) {
            resp.sendRedirect(req.getContextPath() + WebRoutes.SIGN_IN);
            return false;
        }

        UUID sessionId = convertToSessionId(cookie.getValue());

        SessionResponseDto sessionResponseDto = sessionService
                .get(sessionId)
                .filter(Predicate.not(sessionService::isSessionExpired))
                .orElseThrow(SessionNotFoundOrExpiredException::new);

        return true;
    }

    private UUID convertToSessionId(String uuidAsString) {
        try {
            return UUID.fromString(uuidAsString);
        } catch (IllegalArgumentException e) {
            throw new InvalidSessionIdException();
        }
    }
}