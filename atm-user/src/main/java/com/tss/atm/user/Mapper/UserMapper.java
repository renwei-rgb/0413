package com.tss.atm.user.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 你可以在这里添加自定义查询方法
}
