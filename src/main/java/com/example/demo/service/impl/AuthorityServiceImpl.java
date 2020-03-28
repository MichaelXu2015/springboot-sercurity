package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Authority;
import com.example.demo.mapper.AuthorityMapper;
import com.example.demo.service.IAuthorityService;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper,Authority> implements IAuthorityService {

}
