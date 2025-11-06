package com.chatr.auth.dto;

import com.chatr.shared.annotations.NoSpace;
import com.chatr.shared.annotations.ValidLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponseDto(

        @NotNull(message = "Id cannot be null")
        @NotBlank(message = "Id cannot be blank")
        String id,

        @NotNull(message = "Username cannot be null")
        @NoSpace(message = "Username cannot contain spaces")
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotNull(message = "Email cannot be null")
        @NoSpace(message = "Email cannot contain spaces")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @NotNull(message = "Preferred language cannot be null")
        @NoSpace(message = "Preferred language cannot contain spaces")
        @NotBlank(message = "Preferred language cannot be blank")
        @ValidLanguage(message = "Preferred language must be a valid language")
        String preferredLanguage,


        @NotNull(message = "Token cannot be null")
        @NoSpace(message = "Token cannot contain spaces")
        @NotBlank(message = "Token cannot be blank")
        String token
) {
}
