package com.tss.atm.user.controller;

import com.tss.atm.common.dto.LoginRequest;
import com.tss.atm.common.dto.LoginResponse;
import com.tss.atm.common.dto.PasswordChangeRequest;
import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.entity.User;
import com.tss.atm.common.result.Result;
import com.tss.atm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 查询所有用户
    @GetMapping
    public Result<List<User>> getAllUsers() {
        return Result.success(userService.list());
    }

    // 根据 ID 查询用户
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    // 新增用户
    @PostMapping
    public Result<Boolean> createUser(@RequestBody User user) {
        return Result.success(userService.save(user));
    }

    // 更新用户
    @PutMapping
    public Result<Boolean> updateUser(@RequestBody User user) {
        return Result.success(userService.updateById(user));
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        return Result.success(userService.removeById(id));
    }

    // 根据工号查询
    @GetMapping("/employee/{employeeId}")
    public Result<User> getEmployee(@PathVariable String employeeId) {
        return Result.success(userService.getByEmployeeId(employeeId));
    }

    @PostMapping("/{id}/password")
    public boolean updatePassword(@PathVariable Long id, @RequestBody String encodedPwd) {
        return userService.updatePassword(id, encodedPwd);
    }

    @PostMapping("/register")
    public Result<Boolean> registerUser(@RequestBody UserRegisterDTO dto) {
        boolean success = userService.register(dto);
        return Result.success(success);
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return Result.success(userService.login(request));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return Result.success(null);
    }

    @GetMapping("/current")
    public Result<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        return Result.success(userService.getCurrentUser(token));
    }

    @PostMapping("/change-password")
    public Result<Boolean> changePassword(@RequestBody PasswordChangeRequest request) {
        return Result.success(userService.changePassword(
            request.getUserId(), 
            request.getOldPassword(), 
            request.getNewPassword()
        ));
    }

    // 添加供其他服务调用的接口
    @PostMapping("/getUserName")
    public Result<User> getUserByUsername(@RequestBody String username) {
        return Result.success(userService.getByUsername(username));
    }

    @GetMapping("/{id}/roles")
    public Result<List<String>> getUserRoles(@PathVariable Long id) {
        return Result.success(userService.getUserRoles(id));
    }

    @GetMapping("/{id}/permissions")
    public Result<List<String>> getUserPermissions(@PathVariable Long id) {
        return Result.success(userService.getUserPermissions(id));
    }
}
