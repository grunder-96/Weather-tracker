package org.edu.pet.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.exception.InvalidSessionIdException;
import org.edu.pet.exception.SessionNotFoundOrExpiredException;
import org.edu.pet.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AuthInterceptorAdvice {

    @Value("${custom.session.cookie.name}")
    private String customSessionCookieName;

    @ExceptionHandler({SessionNotFoundOrExpiredException.class, InvalidSessionIdException.class})
    public String handleAuthInterceptorExceptions(RedirectAttributes redirectAttributes, HttpServletResponse resp) {

        resp.addCookie(CookieUtil.delete(customSessionCookieName));
        redirectAttributes.addFlashAttribute("errorNotification", "Session not found or expired. Please log in again.");
        return WebRoutes.redirectTo(WebRoutes.SIGN_IN);
    }
}