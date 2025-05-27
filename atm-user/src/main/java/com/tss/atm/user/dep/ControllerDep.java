package com.tss.atm.user.dep;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ControllerDep {
/*
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
    */
}
