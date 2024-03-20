package com.chat.service.service.impl;

import com.chat.service.entity.User;
import com.chat.service.model.UserModel;
import com.chat.service.repo.UserRepo;
import com.chat.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepo userRepo;

    @Override
    public void validateUser(UserModel userModel) throws Exception {
        User user = userRepo.findByUserName(userModel.getUserName());
        if (user != null) {
            if(!user.getPassword().equals(userModel.getPassword()))
                throw new Exception("The password is incorrect.");
        }
        else
            throw new Exception("The user is not present");
    }
}
