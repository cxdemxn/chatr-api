package com.chatr.user.dto;

import com.chatr.shared.enums.PreferredLanguage;
import jakarta.persistence.Id;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record UserDto(@Id Long id, String username, String email, String preferredLanguage, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
