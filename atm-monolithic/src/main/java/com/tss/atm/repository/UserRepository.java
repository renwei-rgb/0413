package com.tss.atm.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository extends BaseMapper<User> {
} 