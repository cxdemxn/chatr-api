package com.chatr.auth.service;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.shared.mapper.AuthMapper;
import com.chatr.shared.mapper.UserMapper;
import com.chatr.user.dto.UserDto;
import com.chatr.user.model.User;
import com.chatr.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private AuthMapper authMapper;
    private UserMapper userMapper;

    public UserDto register(AuthRequestDto requestedUserToRegister) {
        User newUser = authMapper.authRequestDtoToUser(requestedUserToRegister);

        User savedUser = userRepository.save(newUser);

        return userMapper.userToUserDto(savedUser);
    }
}
