package com.chatr.auth.service;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.shared.mapper.AuthMapper;
import com.chatr.shared.mapper.UserMapper;
import com.chatr.user.dto.UserDto;
import com.chatr.user.model.User;
import com.chatr.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(AuthRequestDto requestedUserToRegister) throws UserAlreadyExistsException {

        if (userRepository.existsByEmail(requestedUserToRegister.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if(userRepository.existsByUsername(requestedUserToRegister.username())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User newUser = authMapper.authRequestDtoToUser(requestedUserToRegister);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        User savedUser = userRepository.save(newUser);



        return userMapper.userToUserDto(savedUser);
    }
}
