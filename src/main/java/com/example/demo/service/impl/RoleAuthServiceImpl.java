package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.RoleAuth;
import com.example.demo.mapper.RoleAuthMapper;
import com.example.demo.service.IRoleAuthService;
import org.springframework.stereotype.Service;

@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper,RoleAuth>
implements IRoleAuthService {
}
