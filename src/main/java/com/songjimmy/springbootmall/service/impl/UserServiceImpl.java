package com.songjimmy.springbootmall.service.impl;

import com.songjimmy.springbootmall.dao.UserDao;
import com.songjimmy.springbootmall.dto.UserRegisterRequest;
import com.songjimmy.springbootmall.model.User;
import com.songjimmy.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
