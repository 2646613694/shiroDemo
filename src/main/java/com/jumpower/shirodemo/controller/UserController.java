package com.jumpower.shirodemo.controller;

import com.jumpower.shirodemo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class UserController {

    /**
     *  打开登录界面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    /**
     * 登录的方法
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/doLogin")
    public String doLogin(User user, Model model){
        //构造一个UsernamePasswordToken获取
        UsernamePasswordToken token=new UsernamePasswordToken(user.getName(),user.getPassword());
        //获取Subject对象访问login方法当有异常的时候抛出异常
        Subject subject= SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
           model.addAttribute("error","用户名或密码输入错误");
           return "/login";
        }
        return "redirect:/index";
    }

    /**
     * 打开主页页面
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    /**
     *  打开管理员页面
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/admin")
    @RequiresPermissions("user:admin") //这里是用户对应的权限
    public  String admin(){
        return "/admin";
    }

    /**
     *  打开普通页面
     * @return
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping("/user")
    @RequiresPermissions("user:common") //这里是用户对应的权限
    public  String  user(){
        return "/user";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }



}
