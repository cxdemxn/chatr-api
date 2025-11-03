package com.chatr.user.dto;

import com.chatr.shared.enums.PreferredLanguage;
import jakarta.persistence.Id;

public record UserDto(@Id Long id, String username, String email, String preferredLanguage) {
}
