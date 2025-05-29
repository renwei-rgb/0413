package com.tss.atm.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogRepository extends BaseMapper<LoginLog> {
} 