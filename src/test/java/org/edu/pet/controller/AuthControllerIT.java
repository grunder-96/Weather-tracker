package org.edu.pet.controller;

import jakarta.servlet.http.Cookie;
import org.edu.pet.config.*;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.model.User;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.edu.pet.repository.UserRepository;
import org.edu.pet.util.CookieUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.edu.pet.constant.SessionCookieSettings.COOKIE_NAME;
import static org.edu.pet.constant.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringJUnitWebConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class, WebConfig.class, TestWebConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class AuthControllerIT {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private User defaultUser;

    @BeforeEach
    void setUp() {
        defaultUser = userRepository.save(
                User.builder()
                .login(DEFAULT_USER_LOGIN)
                .password(BCrypt.hashpw(DEFAULT_USER_PASSWORD, BCrypt.gensalt()))
                .build()
        );
    }

    @Test
    void shouldCreateSessionAndRedirectIfAuthIsSuccess() {
        MockHttpServletRequestBuilder builder = post(WebRoutes.SIGN_IN)
                .param("login", defaultUser.getLogin())
                .param("pass", DEFAULT_USER_PASSWORD);

        MvcTestResult actualResult = mockMvcTester.perform(builder);

        assertThat(actualResult).hasRedirectedUrl(WebRoutes.MAIN);
        assertThat(actualResult).cookies()
                .containsCookie(COOKIE_NAME)
                .satisfies(
                    cookies -> {
                        Cookie cookie = cookies.get(COOKIE_NAME);

                        UUID sessionId = UUID.fromString(cookie.getValue());
                        Optional<UserSession> sessionOptional = sessionRepository.findById(sessionId);

                        assertThat(sessionOptional)
                                .map(UserSession::getUser)
                                .contains(defaultUser);
                    }
                );
    }

    @Test
    void shouldRedirectToSignInPageIfSessionIsExpired() {
        UserSession sessionForSave = UserSession.builder()
                .user(defaultUser)
                .expiresAt(LocalDateTime.now())
                .build();
        sessionRepository.save(sessionForSave);
        String sessionIdAsString = sessionForSave.getId().toString();
        MockHttpServletRequestBuilder builder = get(WebRoutes.MAIN)
                .cookie(CookieUtil.create(COOKIE_NAME, sessionIdAsString, sessionForSave.getExpiresAt().plusMinutes(1)));

        MvcTestResult actualResult = mockMvcTester.perform(builder);

        assertThat(actualResult).cookies().hasMaxAge(COOKIE_NAME, Duration.ZERO);
        assertThat(actualResult).hasRedirectedUrl(WebRoutes.SIGN_IN);
        assertThat(actualResult).flash().containsEntry("errorNotification", "Session not found or expired. Please log in again.");
    }
}