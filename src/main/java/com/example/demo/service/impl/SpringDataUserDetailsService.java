package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 从数据库查询用户信息
 * 实现一个用户关联多个角色，一个角色关联多个权限
 * 通过username 查询出这个用户具体关联哪些权限
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IRoleAuthService roleAuthService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IAuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //通过username 从数据库查询用户信息及用户的权限
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserName,s);

        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        System.out.println(" =============== "+s+" =============== "+userInfo);
        if(userInfo == null){
            return null;
        }

        QueryWrapper<UserRole> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda().eq(UserRole::getUserId,userInfo.getUserId());
        //查询用户关联的角色
        List<UserRole> userRoleList = userRoleService.list(queryWrapper2);
        if(userRoleList == null ){
            return null;
        }
        List<RoleAuth> roleAuthList = new ArrayList<>();
        Set<String> roleSet = new HashSet<>();
        userRoleList.forEach((userRole -> {

            QueryWrapper<Role> queryWrapper31 = new QueryWrapper<>();
            queryWrapper31.lambda().eq(Role::getRoleId,userRole.getRoleId());
            Role role = roleService.getOne(queryWrapper31);
            if(role!=null)
            {
                //将这个用户的角色关联到Set
                roleSet.add(role.getRoleName());
            }



            QueryWrapper<RoleAuth> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.lambda().eq(RoleAuth::getRoleId,userRole.getRoleId());
            //有个角色id，查询可能包含多个权限id
            List<RoleAuth> roleAuthList1 = roleAuthService.list(queryWrapper3);
            roleAuthList.addAll(roleAuthList1);
        }));

        Set<String> authSet = new HashSet<>();
        if(roleAuthList!=null && roleAuthList.size()!=0){
            roleAuthList.forEach((roleAuth -> {

                System.out.println(" authId : "+roleAuth.getAuthId() );


                QueryWrapper<Authority> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.lambda().eq(Authority::getAuthId,roleAuth.getAuthId());
                //有个角色id，查询可能包含多个权限id
                Authority authority = authorityService.getOne(queryWrapper4);
               // System.out.println(s+" 拥有的权限 ："+authority.getAuthDesc());
                authSet.add(authority.getAuthDesc());
            }));


            authSet.forEach((auth->{
                //System.out.println(" 去掉重复权限后 ："+auth);
            }));
            //基于权限授权
            String[] permissionArray = authSet.toArray(new String[authSet.size()]);
            for (int i = 0; i <permissionArray.length ; i++) {
                System.out.println("permission : "+permissionArray[i]);
            }
            UserDetails userDetails = User.withUsername(userInfo.getUserName()).password(userInfo.getPassword()).authorities(permissionArray).build();


            //------------------------基于角色授权--------------------------
/*            String[] roleArray = roleSet.toArray(new String[roleSet.size()]);
            for (int i = 0; i <roleArray.length ; i++) {
                System.out.println("role : "+roleArray[i]);
            }
            UserDetails userDetails = User.withUsername(userInfo.getUserName()).password(userInfo.getPassword()).roles(roleArray).build();*/

            return userDetails;
        }
        return null;
    }
}
