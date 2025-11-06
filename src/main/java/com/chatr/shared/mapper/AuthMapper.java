package com.chatr.shared.mapper;

import com.chatr.auth.dto.RegisterUserDto;
import com.chatr.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User authRequestDtoToUser(RegisterUserDto registerUserDto) {
        User mappedUser = new User();

        mappedUser.setUsername(registerUserDto.username());
        mappedUser.setEmail(registerUserDto.email());
        mappedUser.setPassword(registerUserDto.password());
        mappedUser.setPreferredLanguage(registerUserDto.preferredLanguage());

        return mappedUser;
    }
}
