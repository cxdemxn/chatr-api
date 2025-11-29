package com.chatr.message.controller;

import com.chatr.message.dto.MessageRequestDto;
import com.chatr.message.dto.MessageResponseDto;
import com.chatr.user.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/salut")
    @SendTo("/topic/salut")
    public MessageResponseDto sendMessage(User sender, MessageRequestDto messageRequestDto) {
        return null;
    }
}
