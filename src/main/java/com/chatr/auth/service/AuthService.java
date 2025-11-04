package com.chatr.auth.service;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.shared.exceptions.UserAlreadyExistsException;
import com.chatr.user.dto.UserDto;

public interface AuthService {
    UserDto register(AuthRequestDto requestedUserToRegister) throws UserAlreadyExistsException;
}
