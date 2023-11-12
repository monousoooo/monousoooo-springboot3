package com.monousoooo.example.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monousoooo.example.model.entity.SysMenu;
import com.monousoooo.example.util.Response;

import java.util.List;
import java.util.Set;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findMenuByRoleId(Long roleId);

    Response removeMenuById(Long id);

    Boolean updateMenuById(SysMenu sysMenu);

    List<Tree<Long>> treeMenu(Long parentId, String menuName, String type);

    List<Tree<Long>> filterMenu(Set<SysMenu> voSet, String type, Long parentId);
}
