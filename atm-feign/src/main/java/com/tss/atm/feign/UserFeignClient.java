package com.tss.atm.feign;

import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.entity.User;
import com.tss.atm.common.result.Result;
import com.tss.atm.feign.config.FeignConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.cloud.openfeign.FeignClient(
        name = "atm-user",
        configuration = FeignConfig.class
)
public interface UserFeignClient {
    @GetMapping("/users/{id}")
    Result<User> getUserById(@PathVariable("id") Long id);

    @GetMapping("/users/employee/{employeeId}")
    Result<User> getByEmployeeId(@PathVariable("employeeId") String employeeId);

    @PostMapping("/users/getUserName")
    Result<User> getUserByUsername(@RequestBody String username);

    @GetMapping("/users/{id}/roles")
    Result<List<String>> getUserRoles(@PathVariable("id") Long id);

    @GetMapping("/users/{id}/permissions")
    Result<List<String>> getUserPermissions(@PathVariable("id") Long id);

    @PostMapping("/users/register")
    Result<Boolean> registerUser(@RequestBody UserRegisterDTO dto);

    @PostMapping("/users/{id}/password")
    Result<Boolean> updatePassword(@PathVariable("id") Long id, @RequestBody String encodedPwd);
}
