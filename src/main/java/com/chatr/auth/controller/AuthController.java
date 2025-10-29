package com.chatr.auth.controller;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.auth.service.AuthService;
import com.chatr.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<UserDto> register(@RequestBody AuthRequestDto registerRequest) {
        UserDto savedUser = authService.register(registerRequest);

        URI createdResource = URI.create("/api/users/" + savedUser.id());

        return ResponseEntity.created(createdResource).body(savedUser);
    }
}
