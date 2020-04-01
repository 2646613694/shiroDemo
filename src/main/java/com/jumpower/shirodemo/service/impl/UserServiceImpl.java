package com.jumpower.shirodemo.service.impl;

import com.jumpower.shirodemo.entity.User;
import com.jumpower.shirodemo.mapper.UserMapper;
import com.jumpower.shirodemo.service.UserService;

import javax.annotation.Resource;

/**
 * @author xka
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User loginUser(String name) {
        return userMapper.loginUser(name);
    }
}
