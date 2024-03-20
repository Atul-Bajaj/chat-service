package com.chat.service.service;

import com.chat.service.model.UserModel;

public interface UserService {
    void validateUser(UserModel userModel) throws Exception;
}
