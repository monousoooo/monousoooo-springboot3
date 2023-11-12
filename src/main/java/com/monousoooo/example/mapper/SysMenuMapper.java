package com.monousoooo.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monousoooo.example.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> listMenusByRoleId(Long roleId);
}
