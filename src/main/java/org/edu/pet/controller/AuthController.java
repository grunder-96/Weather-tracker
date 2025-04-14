package org.edu.pet.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.req.AuthUserDto;
import org.edu.pet.dto.resp.AuthResponseDto;
import org.edu.pet.service.AuthService;
import org.edu.pet.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @Value("${custom.session.cookie.name}")
    private String customSessionCookieName;
    private final AuthService authService;

    @GetMapping(WebRoutes.SIGN_IN)
    public String getSignInPage(@ModelAttribute AuthUserDto authUserDto) {
        return TemplateNames.SIGN_IN;
    }

    @PostMapping(WebRoutes.SIGN_IN)
    public String signIn(@ModelAttribute @Valid AuthUserDto authUserDto,
                         BindingResult bindingResult,
                         HttpServletResponse resp) {

        if (bindingResult.hasErrors()) {
            return TemplateNames.SIGN_IN;
        }

        AuthResponseDto authResponseDto = authService.authenticate(authUserDto);

        Cookie cookie = CookieUtil.create(
            customSessionCookieName,
            authResponseDto.sessionId(),
            authResponseDto.sessionExpirationTime()
        );

        resp.addCookie(cookie);

        return WebRoutes.redirectTo(WebRoutes.MAIN);
    }
}