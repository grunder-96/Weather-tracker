package org.edu.pet.advice;

import org.edu.pet.constant.WebRoutes;
import org.edu.pet.exception.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AuthAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException  e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorNotification", e.getMessage());
        return WebRoutes.redirectTo(WebRoutes.SIGN_IN);
    }
}