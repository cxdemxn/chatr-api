package com.chatr.auth.dto;

import com.chatr.shared.annotations.NoSpace;
import com.chatr.shared.annotations.ValidLanguage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RegisterResponseDto(
        @NotNull(message = "Username cannot be null")
        @NoSpace(message = "Username cannot contain spaces")
        @NotBlank(message = "Username cannot be blank")
        Long id,

        @NotNull(message = "Username cannot be null")
        @NoSpace(message = "Username cannot contain spaces")
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotNull(message = "Email cannot be null")
        @NoSpace(message = "Email cannot contain spaces")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Valid email is required")
        String email,

        @NotNull(message = "Password cannot be null")
        @NoSpace(message = "Password cannot contain spaces")
        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotNull(message = "Preferred language cannot be null")
        @NoSpace(message = "Preferred language cannot contain spaces")
        @NotBlank(message = "Preferred language cannot be blank")
        @ValidLanguage(message = "Preferred language must be a valid language")
        String preferredLanguage,

        @NotNull(message = "Password cannot be null")
        @NoSpace(message = "Password cannot contain spaces")
        @NotBlank(message = "Password cannot be blank")
        LocalDateTime createdAt,

        @NotNull(message = "Password cannot be null")
        @NoSpace(message = "Password cannot contain spaces")
        @NotBlank(message = "Password cannot be blank")
        LocalDateTime updatedAt
) {
}