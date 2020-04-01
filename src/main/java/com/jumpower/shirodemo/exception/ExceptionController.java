package com.jumpower.shirodemo.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * 创建全局异常处理器进行异常的处理
 * @author xka
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView error(AuthorizationException e){
        ModelAndView modelAndView=new ModelAndView("unauthorized");
        modelAndView.addObject("error",e.getMessage());
        return modelAndView;
    }
}
