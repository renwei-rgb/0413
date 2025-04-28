package com.tss.atm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.auth.entity.User;
import com.tss.atm.auth.mapper.UserMapper;
import com.tss.atm.auth.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getByEmployeeId(String employeeId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmployeeId, employeeId);
        return getOne(wrapper);
    }

    @Override
    public List<User> getByDepartment(String department) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDepartment, department);
        return list(wrapper);
    }
} 