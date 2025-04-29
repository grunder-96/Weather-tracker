package org.edu.pet.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.pet.constant.SessionCookieSettings;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.dto.req.AuthUserDto;
import org.edu.pet.dto.resp.AuthResponseDto;
import org.edu.pet.service.AuthService;
import org.edu.pet.service.SessionService;
import org.edu.pet.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

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
            SessionCookieSettings.COOKIE_NAME,
            authResponseDto.sessionId(),
            authResponseDto.sessionExpirationTime()
        );

        resp.addCookie(cookie);

        return WebRoutes.redirectTo(WebRoutes.MAIN);
    }

    @PostMapping(WebRoutes.SIGN_OUT)
    public String signOut(@CookieValue(SessionCookieSettings.COOKIE_NAME) String sessionIdAsString,
                          HttpServletResponse resp,
                          RedirectAttributes redirectAttributes) {

        sessionService.remove(sessionIdAsString);
        resp.addCookie(CookieUtil.delete(SessionCookieSettings.COOKIE_NAME));
        redirectAttributes.addFlashAttribute("successNotification",
                "You've successfully signed out. To continue working, please reauthorize.");

        return WebRoutes.redirectTo(WebRoutes.SIGN_IN);
    }
}