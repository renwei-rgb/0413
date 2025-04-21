package com.tss.atm;

import com.tss.atm.common.Result;
import com.tss.atm.entity.User;
import com.tss.atm.feign.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthApplicationTests {

    @Autowired
    private UserFeignClient userFeignClient;

    @Test
    void testUserFeignClient() {
        // 创建用户
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setEmail("test@example.com");
        user.setPhone("13800138000");
        user.setStatus(1);
        
        Result<Boolean> createResult = userFeignClient.createUser(user);
        System.out.println("Create user result: " + createResult);
        
        // 获取用户
        Result<User> getUserResult = userFeignClient.getUserById(1L);
        System.out.println("Get user result: " + getUserResult);
        
        // 更新用户
        user.setEmail("updated@example.com");
        Result<Boolean> updateResult = userFeignClient.updateUser(user);
        System.out.println("Update user result: " + updateResult);
        
        // 删除用户
        Result<Boolean> deleteResult = userFeignClient.deleteUser(1L);
        System.out.println("Delete user result: " + deleteResult);
    }
} 