package org.edu.pet.advice;

import org.edu.pet.constant.TemplateNames;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnexpectedCasesAdvice {

    @ExceptionHandler(RuntimeException.class)
    public String handleUnexpectedCase(Exception e) {

        return TemplateNames.ERROR;
    }
}