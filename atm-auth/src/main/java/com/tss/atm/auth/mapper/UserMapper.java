package com.tss.atm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.auth.login.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
