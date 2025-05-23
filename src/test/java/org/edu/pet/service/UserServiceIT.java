package org.edu.pet.service;

import org.edu.pet.config.AppConfig;
import org.edu.pet.config.HibernateConfig;
import org.edu.pet.config.TestHibernateConfig;
import org.edu.pet.dto.req.CreateUserDto;
import org.edu.pet.model.User;
import org.edu.pet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.edu.pet.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldThrowExceptionIfUserAlreadyExists() {
        CreateUserDto createUserDto = new CreateUserDto(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD);
        userService.create(createUserDto);
        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(DEFAULT_USER_LOGIN);

        assertAll(
            () -> assertThat(userOptional).isPresent(),
            () -> assertThatThrownBy(() -> userService.create(createUserDto))
                .isInstanceOf(DataIntegrityViolationException.class)
        );
    }
}