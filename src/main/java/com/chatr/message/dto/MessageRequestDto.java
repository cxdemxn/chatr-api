package com.chatr.message.dto;

public record MessageRequestDto(
        String receiverId,
        String content
) {}
