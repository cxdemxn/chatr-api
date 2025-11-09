package com.chatr.auth.controller;

import com.chatr.auth.dto.LoginRequestDto;
import com.chatr.auth.dto.LoginResponseDto;
import com.chatr.auth.dto.RegisterUserDto;
import com.chatr.auth.service.AuthService;
import com.chatr.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserDto registerRequest) {
        logger.info("register endpoint requested by {}", registerRequest.email());

        UserDto savedUser = authService.register(registerRequest);

        URI createdResource = URI.create("/api/users/" + savedUser.id());

        logger.info("register endpoint responded to {}", savedUser.email());
        return ResponseEntity.created(createdResource).body(savedUser);
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        logger.info("login endpoint requested by {}", loginRequest.username());

        LoginResponseDto loginResponseDto = authService.login(loginRequest);

        logger.info("login endpoint responded to {}", loginResponseDto.username());
        return ResponseEntity.ok(loginResponseDto);
    }
}
