package org.edu.pet.controller;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.edu.pet.config.*;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.edu.pet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.edu.pet.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringJUnitWebConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class, WebConfig.class, TestWebConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
@RequiredArgsConstructor
public class AuthController_IT {

    @Value("${custom.session.cookie.name}")
    String customSessionCookieName;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @BeforeEach
    public void insertDefaultUser() {
        userRepository.save(defaultUser());
    }

    @Test
    public void whenSuccess_ThenSessionCreatedAndRedirected() throws Exception {

        ResultActions result = mockMvc.perform(post(WebRoutes.SIGN_IN)
                .param("login", defaultUser().getLogin())
                .param("pass", defaultUser().getPassword()));

        assertAll(
            () -> result.andExpect(cookie().exists(customSessionCookieName)),
            () -> result.andExpect(redirectedUrl(WebRoutes.MAIN)),
            () -> result.andDo(res -> {
                Cookie cookie = res.getResponse().getCookie(customSessionCookieName);
                UUID sessionId = UUID.fromString(cookie.getValue());
                Optional<UserSession> savedSession = sessionRepository.findById(sessionId);

                assertThat(savedSession).isPresent()
                    .hasValueSatisfying(session -> assertThat(session.getUser().getLogin())
                            .isEqualToIgnoringCase(defaultUser().getLogin())
                    );
                }
            )
        );
    }
}