package com.tss.atm.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")  // 映射数据库中的 "user" 表
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
