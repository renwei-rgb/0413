package com.tss.atm.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleRepository extends BaseMapper<Role> {
} 