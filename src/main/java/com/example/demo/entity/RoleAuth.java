package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 角色关联权限
 */
@Data
@ToString
public class RoleAuth {

    private Integer roleId;
    private Integer authId;

}
