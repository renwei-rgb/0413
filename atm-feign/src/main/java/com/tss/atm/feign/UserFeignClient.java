package com.tss.atm.feign;

import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.entity.User;
import com.tss.atm.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "atm-user")
public interface UserFeignClient {
    @GetMapping("/user/username/{username}")
    Result<User> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/user/roles/{userId}")
    Result<List<String>> getUserRoles(@PathVariable("userId") Long userId);

    @GetMapping("/user/permissions/{userId}")
    Result<List<String>> getUserPermissions(@PathVariable("userId") Long userId);

    @PostMapping("/user/register")
    Result<Boolean> registerUser(@RequestBody UserRegisterDTO userRegisterDTO);

    @GetMapping("/user/{id}")
    Result<User> getUserById(@PathVariable("id") Long id);

    @PutMapping("/user/{id}/password")
    Result<Boolean> updatePassword(@PathVariable("id") Long id, @RequestParam("password") String password);
}
