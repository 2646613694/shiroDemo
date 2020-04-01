package com.jumpower.shirodemo.mapper;

import com.jumpower.shirodemo.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    /**
     *  根据用户名查询相应角色
     * @param name
     * @return
     */
    public List<Role> getRolesByName(@Param("name")String name);
}
