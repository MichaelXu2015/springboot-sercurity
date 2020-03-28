package com.example.demo;

import com.example.demo.service.IUserInfoService;
import com.example.demo.service.impl.SpringDataUserDetailsService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    SpringDataUserDetailsService springDataUserDetailsService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSelect(){
        springDataUserDetailsService.loadUserByUsername("lisi");
        System.out.println(userInfoService.list());
    }

    @Test
    public void testBCrypt(){
        String password = BCrypt.hashpw("123",BCrypt.gensalt());
        System.out.println("password: "+password);

    }

    @Test
    public void testMd5(){
        String encode = MD5Encoder.encode("123".getBytes());
        System.out.println("encode : "+encode);
    }

}
