package org.edu.pet.controller;

import jakarta.servlet.http.Cookie;
import org.edu.pet.config.*;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.model.User;
import org.edu.pet.model.UserSession;
import org.edu.pet.repository.SessionRepository;
import org.edu.pet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.edu.pet.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class, WebConfig.class, TestWebConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class AuthController_IT {

    @Value("${custom.session.cookie.name}")
    String customSessionCookieName;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    private Long defaultUserId;

    @BeforeEach
    public void setUp() {

        User defaultUser = userRepository.save(User.builder()
                .login(DEFAULT_USER_LOGIN)
                .password(BCrypt.hashpw(DEFAULT_USER_PASSWORD, BCrypt.gensalt()))
                .build());

        defaultUserId = defaultUser.getId();
    }

    @Test
    public void whenSuccess_ThenSessionCreatedAndRedirected() throws Exception {

        ResultActions result = mockMvc.perform(post(WebRoutes.SIGN_IN)
                .param("login", DEFAULT_USER_LOGIN)
                .param("pass", DEFAULT_USER_PASSWORD));

        assertAll(
            () -> result.andExpect(cookie().exists(customSessionCookieName)),
            () -> result.andExpect(redirectedUrl(WebRoutes.MAIN)),
            () -> result.andDo(res -> {
                Cookie cookie = res.getResponse().getCookie(customSessionCookieName);
                assertThat(cookie).isNotNull();

                UUID sessionId = UUID.fromString(cookie.getValue());
                Optional<UserSession> sessionOptional = sessionRepository.findById(sessionId);

                assertThat(sessionOptional)
                        .map(UserSession::getUser)
                        .map(User::getId)
                        .contains(defaultUserId);
                }
            )
        );
    }
}