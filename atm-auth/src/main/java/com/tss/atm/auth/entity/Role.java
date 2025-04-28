package com.tss.atm.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@TableName("roles")
@EqualsAndHashCode(exclude = "users")
public class Role {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Set<User> users;
} 