package com.chatr.message.dto;

import java.time.LocalDateTime;

public record MessageResponseDto(
        String id,
        String senderId,
        String receiverId,
        String content,
        String translated,
        LocalDateTime timestamp
) {
}
