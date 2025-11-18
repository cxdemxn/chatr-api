package com.chatr.auth.service;

import com.chatr.auth.dto.LoginRequestDto;
import com.chatr.auth.dto.LoginResponseDto;
import com.chatr.auth.dto.RegisterRequestDto;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.user.dto.UserDto;

public interface AuthService {
    UserDto register(RegisterRequestDto requestedUserToRegister) throws UserAlreadyExistsException;

    LoginResponseDto login(LoginRequestDto requestedUserToLogin);
}
