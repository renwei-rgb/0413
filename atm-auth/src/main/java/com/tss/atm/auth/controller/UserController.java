package com.tss.atm.auth.controller;

//import com.tss.atm.auth.UserService.UserService;
import com.tss.atm.auth.mapper.UserMapper;
import com.tss.atm.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private UserService userService;

    @GetMapping("/list")
    public List<User> list() {
        //return userService.list();
        return userMapper.selectList(null);
    }

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userMapper.insert(user);
        return "User added";
    }
//
//    @PostMapping("/save")
//    public boolean save(@RequestBody User user){
//        return userService.save(user);
//    }
   // gateway
}
