package com.jumpower.shirodemo.mapper;

import com.jumpower.shirodemo.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author xka
 */
public interface UserMapper {
    /**
     *  根据用户名查询
     * @param name
     * @return
     */
    public User loginUser(@Param("name")String name);

}
