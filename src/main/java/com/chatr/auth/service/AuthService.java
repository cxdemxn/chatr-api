package com.chatr.auth.service;

import com.chatr.auth.dto.LoginRequestDto;
import com.chatr.auth.dto.LoginResponseDto;
import com.chatr.auth.dto.RegisterUserDto;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.user.dto.UserDto;

public interface AuthService {
    UserDto register(RegisterUserDto requestedUserToRegister) throws UserAlreadyExistsException;

    LoginResponseDto login(LoginRequestDto requestedUserToLogin);
}
