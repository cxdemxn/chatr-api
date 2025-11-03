package com.chatr.auth.dto;

import com.chatr.shared.annotations.NoSpace;
import com.chatr.shared.annotations.ValidLanguage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
        @NotNull
        @NoSpace
        @NotBlank(message = "Username is required")
        String username,

        @NotNull
        @NoSpace
        @NotBlank(message = "Email is required")
        @Email(message = "Valid email is required")
        String email,

        @NotNull
        @NotBlank
        String password,

        @NotNull
        @NoSpace
        @NotBlank
        @ValidLanguage
        String preferredLanguage
) {}