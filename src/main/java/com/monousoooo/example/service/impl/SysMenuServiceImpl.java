package com.monousoooo.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monousoooo.example.mapper.SysMenuMapper;
import com.monousoooo.example.mapper.SysRoleMenuMapper;
import com.monousoooo.example.model.entity.SysMenu;
import com.monousoooo.example.model.entity.SysRoleMenu;
import com.monousoooo.example.service.SysMenuService;
import com.monousoooo.example.common.util.Response;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response removeMenuById(Long id) {
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id));
        if (CollUtil.isNotEmpty(menuList)) {
            return Response.failed("菜单含有下级不能删除");
        }

        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getMenuId, id));
        // 删除当前菜单及其子菜单
        return Response.ok(this.removeById(id));
    }

    @Override
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }

    @Override
    public List<Tree<Long>> treeMenu(Long parentId, String menuName, String type) {
        Long parent = parentId == null ? 0 : parentId;

        List<TreeNode<Long>> collect = baseMapper
                .selectList(Wrappers.<SysMenu>lambdaQuery()
                        .like(StrUtil.isNotBlank(menuName), SysMenu::getName, menuName)
                        .eq(StrUtil.isNotBlank(type), SysMenu::getMenuType, type)
                        .orderByAsc(SysMenu::getSort))
                .stream()
                .map(getNodeFunction())
                .collect(Collectors.toList());

        // 模糊查询 不组装树结构 直接返回 表格方便编辑
        if (StrUtil.isNotBlank(menuName)) {
            return collect.stream().map(node -> {
                Tree<Long> tree = new Tree<>();
                tree.putAll(node.getExtra());
                BeanUtils.copyProperties(node, tree);
                return tree;
            }).collect(Collectors.toList());
        }

        return TreeUtil.build(collect, parent);
    }

    @Override
    public List<Tree<Long>> filterMenu(Set<SysMenu> voSet, String type, Long parentId) {
        List<TreeNode<Long>> collect = voSet.stream()
                .filter(menuTypePredicate(type))
                .map(getNodeFunction())
                .collect(Collectors.toList());

        Long parent = parentId == null ? 0 : parentId;
        return TreeUtil.build(collect, parent);
    }

    @NotNull
    private Function<SysMenu, TreeNode<Long>> getNodeFunction() {
        return menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("path", menu.getPath());
            extra.put("menuType", menu.getMenuType());
            extra.put("permission", menu.getPermission());
            extra.put("sort", menu.getSort());

            // 适配 vue3
            Map<String, Object> meta = new HashMap<>();
            meta.put("title", menu.getName());
            meta.put("isLink", menu.getPath() != null && menu.getPath().startsWith("http") ? menu.getPath() : "");
            meta.put("isHide", !BooleanUtil.toBooleanObject(menu.getVisible()));
            meta.put("isKeepAlive", BooleanUtil.toBooleanObject(menu.getKeepAlive()));
            meta.put("isAffix", false);
            meta.put("icon", menu.getIcon());
            extra.put("meta", meta);
            node.setExtra(extra);
            return node;
        };
    }

    private Predicate<? super SysMenu> menuTypePredicate(String type) {
        return vo -> {
            if ("top".equals(type)) {
                return "2".equals(vo.getMenuType());
            }
            // 其他查询 左侧 + 顶部
            return !"1".equals(vo.getMenuType());
        };
    }

}
