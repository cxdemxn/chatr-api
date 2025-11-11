package com.chatr.shared.mapper;

import com.chatr.auth.dto.RegisterRequestDto;
import com.chatr.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User registerRequestDtoToUser(RegisterRequestDto registerRequestDto) {
        User mappedUser = new User();

        mappedUser.setUsername(registerRequestDto.username());
        mappedUser.setEmail(registerRequestDto.email());
        mappedUser.setPassword(registerRequestDto.password());
        mappedUser.setPreferredLanguage(registerRequestDto.preferredLanguage());

        return mappedUser;
    }
}
