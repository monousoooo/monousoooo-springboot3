package com.monousoooo.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.monousoooo.example.model.entity.SysRole;
import com.monousoooo.example.model.vo.RoleVO;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> findRolesByUserId(Long userId);

    List<SysRole> findRolesByRoleIds(List<Long> roleIdList, String key);

    Boolean removeRoleByIds(Long[] ids);

    Boolean updateRoleMenus(RoleVO roleVo);


}
