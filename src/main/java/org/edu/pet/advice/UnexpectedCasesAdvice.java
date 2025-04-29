package org.edu.pet.advice;

import org.edu.pet.constant.TemplateNames;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnexpectedCasesAdvice {

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedCase() {

        return TemplateNames.ERROR;
    }
}