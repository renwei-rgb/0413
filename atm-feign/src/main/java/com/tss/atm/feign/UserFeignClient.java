package com.tss.atm.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "atm-user", path = "/user")
public interface UserFeignClient {
//
//    @GetMapping("/{id}")
//    Result<User> getUserById(@PathVariable Long id);
//
//    @PostMapping
//    Result<Boolean> createUser(@RequestBody User user);
//
//    @PutMapping
//    Result<Boolean> updateUser(@RequestBody User user);
//
//    @DeleteMapping("/{id}")
//    Result<Boolean> deleteUser(@PathVariable Long id);
} 