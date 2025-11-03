package com.chatr.auth.service;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.shared.exceptions.ValidationException;
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
public class AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(AuthRequestDto requestedUserToRegister) throws UserAlreadyExistsException {

        if (userRepository.findByEmail(requestedUserToRegister.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if(userRepository.findByUsername(requestedUserToRegister.username()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

//        validation checks
        if (requestedUserToRegister.username() == null) {
            throw new ValidationException("Username not provided");
        }

        User newUser = authMapper.authRequestDtoToUser(requestedUserToRegister);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(newUser);



        return userMapper.userToUserDto(savedUser);
    }
}
