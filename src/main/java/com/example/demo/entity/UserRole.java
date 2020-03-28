package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 用户关联角色
 */
@Data
@ToString
public class UserRole {

    private String userId;
    private Integer roleId;


}
