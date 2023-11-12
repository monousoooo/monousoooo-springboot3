package com.monousoooo.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monousoooo.example.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> listRolesByUserId(Long userId);
}
