package com.chatr.message.service;

import com.chatr.message.dto.MessageRequestDto;
import com.chatr.message.dto.MessageResponseDto;
import com.chatr.user.model.User;

public interface MessageService {
    public MessageResponseDto sendMessage(User sender, MessageRequestDto messageRequestDto);
}
