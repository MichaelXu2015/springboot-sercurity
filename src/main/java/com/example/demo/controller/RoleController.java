package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于方法级别 角色的权限控制
 */
@RestController
public class RoleController {


    @GetMapping("/onlyAdmin")
    @PreAuthorize(value = "hasAnyRole('admin')")
    public String onlyAdmin(){
        return "onlyAdmin";
    }


    @GetMapping("/onlyManager")
    @PreAuthorize(value = "hasAnyRole('manager')")
    public String onlyManager(){
        return "onlyManager";
    }
}
