package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.req.AuthUserDto;
import org.edu.pet.dto.resp.AuthResponseDto;
import org.edu.pet.exception.AuthenticationException;
import org.edu.pet.mapper.SessionMapper;
import org.edu.pet.model.User;
import org.edu.pet.model.UserSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final SessionService sessionService;

    private final SessionMapper sessionMapper;

    @Transactional
    public AuthResponseDto authenticate(AuthUserDto authUserDto) {

        Optional<User> userOptional = userService.findByLoginInternal(authUserDto.login());

        User user = userOptional
                .filter(u -> BCrypt.checkpw(authUserDto.pass(), u.getPassword()))
                .orElseThrow(AuthenticationException::new);

        UserSession session = sessionService.createInternal(user);

        return sessionMapper.toAuthDto(session);
    }
}