package com.example.demo.config;

import com.example.demo.service.IUserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //开启注解授权
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IUserInfoService userInfoService;

    //定义用户信息服务 使用内存发给
/*    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("$2a$10$loqBYbt8tYrkLnKnzYRpN.g96UogR/b4ra5YHJG4OUHVZIN8Xd.vC").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("123").authorities("p2").build());
        return manager;
    }*/

    //李四使用 密码直接明文比较
/*
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
*/


    /**
     * 张三使用 加密方式验证密码
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //定义安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                //这里注释掉的可在SpringDataUserDetailsService 配置
                // UserDetails userDetails = User.withUsername(userInfo.getUserName()).password(userInfo.getPassword()).authorities(permissionArray).build();
                //上一行的代码相当于从数据库查询了一些具体的的权限,然后配合注解PreAuthorize(value = "hasAnyAuthority('delete')")标记在具体的方法上使用
             /*   @GetMapping("/delete/order")
                @PreAuthorize(value = "hasAnyAuthority('delete')")
                public String deleteOrder(){
                    return "deleteOrder";
                }*/

 /*               .antMatchers("/create/order").hasAnyAuthority("create")
                .antMatchers("/find/order").hasAnyAuthority("find")
                .antMatchers("/update/order").hasAnyAuthority("update")
                .antMatchers("/delete/order").hasAnyAuthority("delete")*/

                //create/order1 是具体的url, create 是具体的权限,用户是从这SpringDataUserDetailsService查询具体的权限的
                //UserDetails userDetails = User.withUsername(userInfo.getUserName()).password(userInfo.getPassword()).authorities(permissionArray).build();
                .antMatchers("/create/order1").hasAnyAuthority("create")
                .antMatchers("/find/order1").hasAnyAuthority("find")
                .antMatchers("/update/order1").hasAnyAuthority("update")
                .antMatchers("/delete/order1").hasAnyAuthority("delete")
                .antMatchers("/r/r1").hasAnyAuthority("p1") //相当于是角色关联权限
                .antMatchers("/r/r2").hasAnyAuthority("p2")
                .antMatchers("/r/**") //所有/r/** 的请求必须验证通过,其他的可以通过
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                //.loginPage("/login") //不使用SpringSecurity默认的登录页面，这里会指向到LoginController的login方法
                .successForwardUrl("/login-success");

        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403 返回自定义JSON
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
