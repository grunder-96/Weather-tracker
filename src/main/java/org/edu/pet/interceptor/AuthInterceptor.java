package org.edu.pet.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.SessionCookieSettings;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.resp.SessionResponseDto;
import org.edu.pet.exception.SessionNotFoundOrExpiredException;
import org.edu.pet.service.SessionService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Cookie cookie = WebUtils.getCookie(req, SessionCookieSettings.COOKIE_NAME);

        if (cookie == null) {
            resp.sendRedirect(req.getContextPath() + WebRoutes.SIGN_IN);
            return false;
        }

        SessionResponseDto sessionResponseDto = sessionService
                .get(cookie.getValue())
                .filter(Predicate.not(sessionService::isSessionExpired))
                .orElseThrow(SessionNotFoundOrExpiredException::new);

        req.setAttribute("user", sessionResponseDto.user());

        return true;
    }
}