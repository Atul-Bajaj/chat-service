package com.chat.service.controller;

import com.chat.service.entity.ChatDetails;
import com.chat.service.entity.User;
import com.chat.service.enums.MessageType;
import com.chat.service.model.UserModel;
import com.chat.service.repo.UserRepo;
import com.chat.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    public UserService userService;

    //TODO: Remove this api validation and add authorization interceptor at the time of create socket connection from client
    @PostMapping(value = "validate")
    public ResponseEntity<?> validateUser(@RequestBody UserModel userModel) {
        try {
            userService.validateUser(userModel);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
