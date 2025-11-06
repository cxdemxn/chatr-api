package com.chatr.auth.dto;

import com.chatr.shared.annotations.NoSpace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(

        @NotNull(message = "Username cannot be null")
        @NoSpace(message = "Username cannot contain spaces")
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotNull(message = "Password cannot be null")
        @NoSpace(message = "Password cannot contain spaces")
        @NotBlank(message = "Password cannot be blank")
        String password
) {}
