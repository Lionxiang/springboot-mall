package com.songjimmy.springbootmall.dao;

import com.songjimmy.springbootmall.dto.UserRegisterRequest;
import com.songjimmy.springbootmall.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
