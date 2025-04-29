package com.tss.atm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
