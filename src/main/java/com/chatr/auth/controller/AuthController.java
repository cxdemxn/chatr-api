package com.chatr.auth.controller;

import com.chatr.auth.dto.LoginRequestDto;
import com.chatr.auth.dto.RegisterUserDto;
import com.chatr.auth.service.AuthService;
import com.chatr.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    private ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserDto registerRequest) {
        UserDto savedUser = authService.register(registerRequest);

        URI createdResource = URI.create("/api/users/" + savedUser.id());

        return ResponseEntity.created(createdResource).body(savedUser);
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok("Login successful");
    }
}
