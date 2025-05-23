package org.edu.pet.controller;

import org.edu.pet.config.*;
import org.edu.pet.constant.TemplateNames;
import org.edu.pet.constant.WebRoutes;
import org.edu.pet.model.User;
import org.edu.pet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.edu.pet.constant.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringJUnitWebConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class, WebConfig.class, TestWebConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class RegisterControllerIT {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUserAndRedirectIfRegisterDataIsValid() {
        MockHttpServletRequestBuilder builder = getBuilder();

        MvcTestResult actualResult = mockMvcTester.perform(builder);

        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(DEFAULT_USER_LOGIN);
        assertThat(actualResult).hasRedirectedUrl(WebRoutes.SIGN_IN);
        assertThat(actualResult).flash().containsKeys("successNotification");
        assertThat(userOptional)
                .map(User::getLogin)
                .hasValueSatisfying(login -> assertThat(login).isEqualToIgnoringCase(DEFAULT_USER_LOGIN));
    }

    @Test
    void shouldReturnRegisterPageIfPasswordConfirmIsIncorrect() {
        MockHttpServletRequestBuilder builder = getBuilder(
                DEFAULT_USER_LOGIN,
                DEFAULT_USER_PASSWORD,
                "dummy"
        );

        MvcTestResult actualResult = mockMvcTester.perform(builder);

        assertThat(actualResult).hasViewName(TemplateNames.SIGN_UP);
        assertThat(actualResult).model().containsEntry("errorNotification", "Passwords don't match");
    }

    @Test
    void shouldReturnRegisterPageIfEmailAlreadyExists() {
        User savedUser = User.builder()
                .login(DEFAULT_USER_LOGIN)
                .password(BCrypt.hashpw(DEFAULT_USER_LOGIN, BCrypt.gensalt()))
                .build();
        userRepository.save(savedUser);
        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(DEFAULT_USER_LOGIN);
        assertThat(userOptional).isPresent();

        MvcTestResult actualResult = mockMvcTester.perform(getBuilder());

        assertThat(actualResult).hasRedirectedUrl(WebRoutes.SIGN_UP);
        assertThat(actualResult).flash().containsEntry("errorNotification", "User with this email already exists");
    }

    private static MockHttpServletRequestBuilder getBuilder() {
        return getBuilder(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
    }

    private static MockHttpServletRequestBuilder getBuilder(String login, String password, String passwordConfirm) {
        return post(WebRoutes.SIGN_UP)
                .param("login", login)
                .param("pass", password)
                .param("passConfirm", passwordConfirm);
    }
}