package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {



    @RequestMapping("/login")
    public String login(){
        return "这算是登录界面";
    }

    @RequestMapping(value = "/login-success")
    public String loginSuccess(){
      return "登录成功";


    }


    @RequestMapping(value = "/r/r1")
    public String r1(){
        return "访问r1资源";
    }

    @RequestMapping(value = "/r/r2")
    public String r2(){
        return "访问r2资源";
    }

    @GetMapping("/hello")
    public String hello(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Object o = authentication.getPrincipal();
        String username = "";
        if(o == null) {
            username = "匿名";
        }
        if(o instanceof UserDetails){
            UserDetails userDetails = (UserDetails)o;
            username = userDetails.getUsername();
        }
        return " hello : "+username;
    }


    //================測試,方法级别的授权====================

    @GetMapping("/create/order")
    @PreAuthorize(value = "hasAuthority('create')")
    public String createOrder(){
        return "createOrder";
    }

    @GetMapping("/find/order")
    @PreAuthorize(value = "hasAnyAuthority('find')")
    public String findOrder(){
        return "findOrder";
    }

    @GetMapping("/update/order")
    @PreAuthorize(value = "hasAnyAuthority('update')")
    public String updateOrder(){
        return "updateOrder";
    }

    @GetMapping("/delete/order")
    @PreAuthorize(value = "hasAnyAuthority('delete')")
    public String deleteOrder(){
        return "deleteOrder";
    }


    //================測試2,基于在WebSecurityConfig配置授权====================

    @GetMapping("/create/order1")
    @PreAuthorize(value = "hasAuthority('create')")
    public String createOrder1(){
        return "createOrder1";
    }

    @GetMapping("/find/order1")
    @PreAuthorize(value = "hasAnyAuthority('find')")
    public String findOrder1(){
        return "findOrder1";
    }

    @GetMapping("/update/order1")
    @PreAuthorize(value = "hasAnyAuthority('update')")
    public String updateOrder1(){
        return "updateOrder1";
    }

    @GetMapping("/delete/order1")
    @PreAuthorize(value = "hasAnyAuthority('delete')")
    public String deleteOrder1(){
        return "deleteOrder1";
    }


}
