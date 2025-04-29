package org.edu.pet.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.edu.pet.constant.SessionCookieSettings;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.exception.InvalidSessionIdException;
import org.edu.pet.exception.SessionNotFoundOrExpiredException;
import org.edu.pet.util.CookieUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AuthInterceptorAdvice {

    @ExceptionHandler({SessionNotFoundOrExpiredException.class, InvalidSessionIdException.class})
    public String handleAuthInterceptorExceptions(RedirectAttributes redirectAttributes, HttpServletResponse resp) {

        resp.addCookie(CookieUtil.delete(SessionCookieSettings.COOKIE_NAME));
        redirectAttributes.addFlashAttribute("errorNotification", "Session not found or expired. Please log in again.");
        return WebRoutes.redirectTo(WebRoutes.SIGN_IN);
    }
}