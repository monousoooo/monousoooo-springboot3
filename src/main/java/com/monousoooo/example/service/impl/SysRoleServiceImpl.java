package com.monousoooo.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monousoooo.example.mapper.SysRoleMapper;
import com.monousoooo.example.model.entity.SysRole;
import com.monousoooo.example.model.entity.SysRoleMenu;
import com.monousoooo.example.model.vo.RoleVO;
import com.monousoooo.example.service.SysRoleMenuService;
import com.monousoooo.example.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private SysRoleMenuService roleMenuService;

    @Override
    public List<SysRole> findRolesByUserId(Long userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    @Override
    public List<SysRole> findRolesByRoleIds(List<Long> roleIdList, String key) {
        return baseMapper.selectBatchIds(roleIdList);
    }

    @Override
    public Boolean removeRoleByIds(Long[] ids) {
        roleMenuService
                .remove(Wrappers.<SysRoleMenu>update().lambda().in(SysRoleMenu::getRoleId, CollUtil.toList(ids)));
        return this.removeBatchByIds(CollUtil.toList(ids));
    }

    @Override
    public Boolean updateRoleMenus(RoleVO roleVo) {
        return roleMenuService.saveRoleMenus(roleVo.getRoleId(), roleVo.getMenuIds());
    }
}
