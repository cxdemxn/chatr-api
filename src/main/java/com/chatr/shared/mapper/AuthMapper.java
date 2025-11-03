package com.chatr.shared.mapper;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User authRequestDtoToUser(AuthRequestDto authRequestDto) {
        User mappedUser = new User();

        mappedUser.setUsername(authRequestDto.username());
        mappedUser.setEmail(authRequestDto.email());
        mappedUser.setPassword(authRequestDto.password());
        mappedUser.setPreferredLanguage(authRequestDto.preferredLanguage());

        return mappedUser;
    }
}
