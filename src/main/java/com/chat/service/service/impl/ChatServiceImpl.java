package com.chat.service.service.impl;

import com.chat.service.entity.ChatDetails;
import com.chat.service.repo.ChatDetailRepo;
import com.chat.service.repo.UserRepo;
import com.chat.service.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public ChatDetailRepo chatDetailRepo;

    @Override
    public void save(ChatDetails chatMessage) {
        if(userRepo.findByUserName(chatMessage.getUserName()) != null) {
            chatMessage.setCreatedDate(new Date());
            chatMessage.setUpdateDate(new Date());
            chatDetailRepo.save(chatMessage);
        }else
            throw new RuntimeException("User not found");
    }
}
