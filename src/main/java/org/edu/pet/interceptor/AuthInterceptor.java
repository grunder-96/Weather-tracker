package org.edu.pet.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.SessionResponseDto;
import org.edu.pet.exception.InvalidSessionIdException;
import org.edu.pet.exception.SessionNotFoundException;
import org.edu.pet.service.SessionService;
import org.edu.pet.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${cookie.session_id.name}")
    private String sessionCookieName;

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        if (req.getRequestURI().startsWith("/signin")) {
            return true;
        }

        Cookie cookie = WebUtils.getCookie(req, sessionCookieName);

        if (cookie == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return false;
        }

        UUID sessionId = convertToSessionId(cookie.getValue());

        SessionResponseDto sessionResponseDto = sessionService
                .get(sessionId)
                .orElseThrow(() -> {
                    resp.addCookie(CookieUtil.delete(cookie.getName()));
                    return new SessionNotFoundException();
                });

        if (sessionService.isSessionExpired(sessionResponseDto)) {
            resp.addCookie(CookieUtil.delete(cookie.getName()));
            throw new InvalidSessionIdException();
        }

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