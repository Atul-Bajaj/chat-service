package com.chat.service.controller;

import com.chat.service.entity.ChatDetails;
import com.chat.service.enums.MessageType;
import com.chat.service.model.UserModel;
import com.chat.service.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    public ChatService chatService;

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatDetails register(@Payload ChatDetails chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getUserName());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatDetails sendMessage(@Payload ChatDetails chatMessage) {
        chatService.save(chatMessage);
        return chatMessage;
    }
}