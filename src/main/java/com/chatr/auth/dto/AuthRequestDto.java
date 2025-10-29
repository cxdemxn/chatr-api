package com.chatr.auth.dto;

public record AuthRequestDto(String username, String email, String password, String preferredLanguage) {
}
