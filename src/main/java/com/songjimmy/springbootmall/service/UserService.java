package com.songjimmy.springbootmall.service;

import com.songjimmy.springbootmall.dto.UserLoginRequest;
import com.songjimmy.springbootmall.dto.UserRegisterRequest;
import com.songjimmy.springbootmall.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
