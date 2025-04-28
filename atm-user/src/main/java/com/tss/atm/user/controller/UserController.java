package com.tss.atm.user.controller;

import com.tss.atm.auth.mapper.UserMapper;
import com.tss.atm.common.result.Result;
import com.tss.atm.user.service.EmployeeService;
import com.tss.atm.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private EmployeeService employeeService;

    // 查询所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userMapper.selectList(null);  // 查询全部
    }

    // 根据 ID 查询用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userMapper.selectById(id);
    }

    @GetMapping
    public Result<List<User>> getAllEmployees() {
        return Result.success(employeeService.list());
    }

    @PostMapping
    public Result<Boolean> createEmployee(@RequestBody User user) {
        return Result.success(employeeService.save(user));
    }

    @PutMapping
    public Result<Boolean> updateEmployee(@RequestBody User user) {
        return Result.success(employeeService.updateById(user));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteEmployee(@PathVariable Long id) {
        return Result.success(employeeService.removeById(id));
    }

    @GetMapping("/{employeeId}")
    public Result<User> getEmployee(@PathVariable String employeeId) {
        return Result.success(employeeService.getByEmployeeId(employeeId));
    }
}
