package com.chatr.auth.service;

import com.chatr.auth.dto.LoginRequestDto;
import com.chatr.auth.dto.LoginResponseDto;
import com.chatr.auth.dto.RegisterRequestDto;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.shared.mapper.AuthMapper;
import com.chatr.shared.mapper.UserMapper;
import com.chatr.shared.utils.JwtUtils;
import com.chatr.user.dto.UserDto;
import com.chatr.user.model.User;
import com.chatr.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserDto register(RegisterRequestDto requestedUserToRegister) throws UserAlreadyExistsException {

        if (userRepository.existsByEmail(requestedUserToRegister.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if(userRepository.existsByUsername(requestedUserToRegister.username())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User newUser = authMapper.registerRequestDtoToUser(requestedUserToRegister);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        User savedUser = userRepository.save(newUser);


        return userMapper.userToUserDto(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto requestedUserToLogin) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        requestedUserToLogin.username(),
                        requestedUserToLogin.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(authentication);


        return new LoginResponseDto(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPreferredLanguage(),
                jwt);
    }
}
