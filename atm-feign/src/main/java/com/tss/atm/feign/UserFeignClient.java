package com.tss.atm.feign;

import com.tss.atm.common.Result;
import com.tss.atm.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "atm-user", path = "/user")
public interface UserFeignClient {
    
    @GetMapping("/{id}")
    Result<User> getUserById(@PathVariable Long id);
    
    @PostMapping
    Result<Boolean> createUser(@RequestBody User user);
    
    @PutMapping
    Result<Boolean> updateUser(@RequestBody User user);
    
    @DeleteMapping("/{id}")
    Result<Boolean> deleteUser(@PathVariable Long id);
} 