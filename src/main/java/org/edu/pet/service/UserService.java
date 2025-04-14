package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.req.CreateUserDto;
import org.edu.pet.mapper.UserMapper;
import org.edu.pet.model.User;
import org.edu.pet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void create(CreateUserDto createUserDto) {

        userRepository.save(userMapper.toEntity(createUserDto));
    }

    @Transactional(readOnly = true)
    Optional<User> findByLoginInternal(String login) {

        return userRepository.findByLoginIgnoreCase(login);
    }
}