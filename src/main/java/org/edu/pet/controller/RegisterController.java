package org.edu.pet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.req.RegisterFormDto;
import org.edu.pet.mapper.UserDtoMapper;
import org.edu.pet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserDtoMapper userDtoMapper;
    private final UserService userService;

    @GetMapping(WebRoutes.SIGN_UP)
    public String getSignUpPage(@ModelAttribute RegisterFormDto registerFormDto) {

        return TemplateNames.SIGN_UP;
    }

    @PostMapping(WebRoutes.SIGN_UP)
    public String signUp(@ModelAttribute @Valid RegisterFormDto registerFormDto,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (!registerFormDto.pass().equals(registerFormDto.passConfirm())) {
            model.addAttribute("errorNotification", "Passwords don't match");
        }

        if (bindingResult.hasErrors() || model.containsAttribute("errorNotification")) {
            return TemplateNames.SIGN_UP;
        }

        userService.create(userDtoMapper.toCreateUserDto(registerFormDto));

        redirectAttributes.addFlashAttribute("successNotification", "Registration complete. Time to log in!");
        return WebRoutes.redirectTo(WebRoutes.SIGN_IN);
    }
}