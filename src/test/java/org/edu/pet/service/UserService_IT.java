package org.edu.pet.service;

import org.edu.pet.config.AppConfig;
import org.edu.pet.config.HibernateConfig;
import org.edu.pet.config.TestHibernateConfig;
import org.edu.pet.dto.req.CreateUserDto;
import org.edu.pet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class})
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class UserService_IT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenUserAlreadyExists_ThenThrowException() {

        String login = "test@gmail.com";
        String password = "12345678";

        CreateUserDto createUserDto = new CreateUserDto(login, password);

        userService.create(createUserDto);
        assertTrue(userRepository.findByLoginIgnoreCase(login).isPresent());
        assertThrows(DataIntegrityViolationException.class, () -> userService.create(createUserDto));
    }
}