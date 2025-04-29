package org.edu.pet.advice;

import org.edu.pet.constant.WebRoutes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class RegisterAdvice {

    private static final String USERNAME_ALREADY_EXISTS_CONSTRAINT = "unique_users_login_idx";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleUsernameAlreadyExistsSituation(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {

        Throwable current = (Throwable) e;

        while (current != null) {

            if (isUsernameAlreadyExistsSituation(e)) {
                redirectAttributes.addFlashAttribute("errorNotification", "User with this email already exists");
                return WebRoutes.redirectTo(WebRoutes.SIGN_UP);
            }

            current = current.getCause();
        }

        throw e;
    }

    private boolean isUsernameAlreadyExistsSituation(Throwable e) {

        return e.getMessage().toLowerCase()
                .contains(USERNAME_ALREADY_EXISTS_CONSTRAINT.toLowerCase());
    }
}