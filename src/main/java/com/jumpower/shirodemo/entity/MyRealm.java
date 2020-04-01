package com.jumpower.shirodemo.entity;

import com.jumpower.shirodemo.mapper.PermissionMapper;
import com.jumpower.shirodemo.mapper.RoleMapper;
import com.jumpower.shirodemo.mapper.UserMapper;
import com.jumpower.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  自定义Realm
 * @author xka
 */
@Component("authorizer")
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;



    /**
     *  授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("牛逼哥，你好进入了授权方法，奥利给");
        //获取当前登陆者对象用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        if (userName == null) {
            System.out.println("授权失败，用户信息为空！！！");
            return null;
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //根据用户查询相应的角色
        Set<String> roles=new HashSet<String>();
        //获取用户的角色集
        List<Role> rolesByName = roleMapper.getRolesByName(userName);
        for(Role role:rolesByName) {
            roles.add(role.getName());
        }
        ////通过角色获取对应的权限集
        List<Permission> byUserName = permissionMapper.getPermissionsByUserName(userName);
        for(Permission permission:byUserName) {
            info.addStringPermission(permission.getUrl());
        }
        info.addRoles(roles);
        return info;
    }

    /**
     *  认证
     * @param token 存储的是主体(Subject)的身份认证信息
     * AuthenticationInfo对象和AuthenticationToken进行匹配
     * @return 如匹配成功则表示主体(Subject)认证成功，否则表示认证失败
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("token.getPrincipal"+token.getPrincipal());
        System.out.println("token.getCredentials"+token.getCredentials());
        String userName = token.getPrincipal().toString();
        User user = userMapper.loginUser(userName);
        if(user !=null){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
            return authcInfo;
        }else{
            return null;
        }

    }
}
