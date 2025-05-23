package org.edu.pet.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.SessionCookieSettings;
import org.edu.pet.constant.WebRoutes;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

@Component
@RequiredArgsConstructor
public class UnauthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Cookie cookie = WebUtils.getCookie(req, SessionCookieSettings.COOKIE_NAME);

        if (cookie == null) {
            return true;
        }

        resp.sendRedirect(req.getContextPath() + WebRoutes.MAIN);
        return false;
    }
}