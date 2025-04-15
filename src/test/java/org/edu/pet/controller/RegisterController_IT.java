package org.edu.pet.controller;

import org.edu.pet.config.*;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.model.User;
import org.edu.pet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.edu.pet.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringJUnitWebConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class, WebConfig.class, TestWebConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class RegisterController_IT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    public void whenRegisterDataIsValid_ThenUserIsSavedInDbAndRedirectedToLoginPage() throws Exception {

        ResultActions result = mockMvc.perform(post(WebRoutes.SIGN_UP)
                .param("login", defaultUser().getLogin())
                .param("pass", defaultUser().getPassword())
                .param("passConfirm", defaultUser().getPassword()));

        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(defaultUser().getLogin());

        assertAll(
                () -> result.andExpect(redirectedUrl(WebRoutes.SIGN_IN)),
                () -> result.andExpect(flash().attributeExists("successNotification")),
                () -> assertThat(userOptional.isPresent()).isTrue(),
                () -> assertThat(userOptional.get().getLogin()).isEqualTo(defaultUser().getLogin())
        );
    }
}