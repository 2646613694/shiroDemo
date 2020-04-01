package com.jumpower.shirodemo.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.jumpower.shirodemo.entity.MyRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author xka
 */
@Configuration
public class ShiroConfig {

    /**
     *  自定义Realm
     *  直接配置了两个用户
     *   xka=123456 ;admin = admin
     *   角色分别对应两个角色admin和user
     *   其中admin有读写的权限，user只有读的权限（个人理解）
     * @return
     */
    @Bean
    public MyRealm realm(){
        MyRealm myRealm = new MyRealm();
 /*       TextConfigurationRealm realm=new TextConfigurationRealm();
        realm.setUserDefinitions("xka=admin,user\n admin=admin,admin");
        realm.setRoleDefinitions("admin=read,write\n user=read");*/
        return myRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    // 将Realm注册到securityManager中
    @Bean
    public SecurityManager securityManager() {
        //构造安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //
        securityManager.setRealm(realm());
        return securityManager;
    }

    /**
     *  ShiroFilterChainDefinition 拦截器
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //展示统一使用注解做访问控制，所以这里配置所有请求路径都可以匿名访问
        //访问控制
        chainDefinition.addPathDefinition("/login","anon");//可以匿名访问
        chainDefinition.addPathDefinition("/doLogin","anon");//可以匿名访问
        chainDefinition.addPathDefinition("logout","logout");//登出过滤器，配置指定url就可以实现退出功能，非常方便
        chainDefinition.addPathDefinition("/**","authc");//需要登录
        return chainDefinition;
    }
    /**
     * SpringShiroFilter首先注册到spring容器
     * 然后被包装成FilterRegistrationBean
     * 最后通过FilterRegistrationBean注册到servlet容器
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    //设置cookie
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称,对应前端的checkbox的name=rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //记住我cookie生效时间3个小时(单位秒)
        simpleCookie.setMaxAge(10800);
        return simpleCookie;
    }

    //cookie管理对象,记住我功能
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 定义一个在thymealf中支持Shiro标签
     * 祝咱老李(元美)一帆风顺，二龙腾飞，三阳开泰，四季平安，五福临门，六六大顺，七星高照，九九同心，十全十美，百事亨通，千事顺心，万事如意！
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return  new ShiroDialect();
    }
}
