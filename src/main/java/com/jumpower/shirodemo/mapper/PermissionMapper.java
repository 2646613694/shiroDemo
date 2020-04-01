package com.jumpower.shirodemo.mapper;

import com.jumpower.shirodemo.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {

    /**
     * 根据用户名查询相应权限
     * @param name
     * @return
     */
    public List<Permission>  getPermissionsByUserName(@Param("name")String name);
}
