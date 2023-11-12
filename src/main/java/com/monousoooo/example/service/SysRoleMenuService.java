package com.monousoooo.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.monousoooo.example.model.entity.SysRoleMenu;

public interface SysRoleMenuService extends IService<SysRoleMenu>{
    Boolean saveRoleMenus(Long roleId, String menuIds);
}
