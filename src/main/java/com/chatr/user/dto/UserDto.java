package com.chatr.user.dto;

import jakarta.persistence.Id;

public record UserDto(@Id Long id, String username, String email, String preferredLanguage) {
}
