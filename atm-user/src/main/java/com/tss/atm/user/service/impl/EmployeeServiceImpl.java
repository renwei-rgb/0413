package com.tss.atm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.auth.entity.User;
import com.tss.atm.auth.mapper.UserMapper;
import com.tss.atm.user.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<UserMapper, User> implements EmployeeService {



    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByEmployeeId(String employeeId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, employeeId);
        return getOne(wrapper);
    }
    
    @Override
    public boolean validateLogin(String userId, String password) {
        User user = getByEmployeeId(userId);
        if (user == null) {
            return false;
        }
        // 这里应该使用加密后的密码进行比对
//        return user.status() == 1; // 在职状态
        return true;
    }

    @Override
    public List<User> getByDepartment(String department) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDepartment, department);
        return userMapper.selectList(wrapper);
    }
} 