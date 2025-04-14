package org.edu.pet.controller;

import org.edu.pet.constant.TemplateNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String getMainPage() {
        return TemplateNames.MAIN;
    }
}