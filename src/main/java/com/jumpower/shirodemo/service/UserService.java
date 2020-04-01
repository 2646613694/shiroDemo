package com.jumpower.shirodemo.service;

import com.jumpower.shirodemo.entity.User;

/**
 * @author xka
 */
public interface UserService {

    /**
     *  根据用户名查询
     * @param name
     * @return
     */
    public User loginUser(String name);
}
