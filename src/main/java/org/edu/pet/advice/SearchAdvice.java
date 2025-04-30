package org.edu.pet.advice;

import org.edu.pet.constant.TemplateNames;
import org.edu.pet.exception.InvalidKeywordException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SearchAdvice {

    @ExceptionHandler(InvalidKeywordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingRequestParamSituation(Model model, InvalidKeywordException e) {

        model.addAttribute("statusCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("message", e.getMessage());
        return TemplateNames.ERROR;
    }
}