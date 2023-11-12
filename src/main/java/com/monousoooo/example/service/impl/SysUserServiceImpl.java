package com.monousoooo.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monousoooo.example.mapper.SysUserMapper;
import com.monousoooo.example.mapper.SysUserRoleMapper;
import com.monousoooo.example.model.dto.UserDTO;
import com.monousoooo.example.model.dto.UserInfo;
import com.monousoooo.example.model.entity.SysMenu;
import com.monousoooo.example.model.entity.SysRole;
import com.monousoooo.example.model.entity.SysUser;
import com.monousoooo.example.model.entity.SysUserRole;
import com.monousoooo.example.model.vo.UserVO;
import com.monousoooo.example.security.util.SecurityUtils;
import com.monousoooo.example.service.SysMenuService;
import com.monousoooo.example.service.SysRoleService;
import com.monousoooo.example.service.SysUserService;
import com.monousoooo.example.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysMenuService sysMenuService;

    private final SysRoleService sysRoleService;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public UserInfo findUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        // 设置角色列表 （ID）
        List<Long> roleIds = sysRoleService.findRolesByUserId(sysUser.getId())
                .stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roleIds, Long.class));

        // 设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = sysMenuService.findMenuByRoleId(roleId)
                    .stream()
                    .filter(menu -> StrUtil.isNotEmpty(menu.getPermission()))
                    .map(SysMenu::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    @Override
    public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
        return baseMapper.getUserVosPage(page, userDTO);
    }

    @Override
    public Boolean deleteUserByIds(Long[] ids) {
        List<SysUser> userList = baseMapper.selectBatchIds(CollUtil.toList(ids));
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, CollUtil.toList(ids)));
        this.removeBatchByIds(CollUtil.toList(ids));
        return Boolean.TRUE;
    }

    @Override
    public Response<Boolean> updateUserInfo(UserDTO userDto) {
        UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
        SysUser sysUser = new SysUser();
        sysUser.setPhone(userDto.getPhone());
        sysUser.setId(userVO.getId());
        sysUser.setAvatar(userDto.getAvatar());
        sysUser.setNickname(userDto.getNickname());
        return Response.ok(this.updateById(sysUser));
    }

    @Override
    public Boolean updateUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        if (StrUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        // 更新用户角色表
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userDto.getId()));
        userDto.getRole().stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(roleId);
            return userRole;
        }).forEach(SysUserRole::insert);

        return Boolean.TRUE;
    }

    @Override
    public UserVO selectUserVoById(Long id) {
        return baseMapper.getUserVoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);

        // 如果角色为空，赋默认角色
        if (CollUtil.isEmpty(userDto.getRole())) {
            // 获取默认角色编码
            String defaultRole = "user";
            // 默认角色
            SysRole sysRole = sysRoleService
                    .getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, defaultRole));
            userDto.setRole(Collections.singletonList(sysRole.getId()));
        }

        // 插入用户角色关系表
        userDto.getRole().stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(roleId);
            return userRole;
        }).forEach(sysUserRoleMapper::insert);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> registerUser(UserDTO userDto) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userDto.getUsername()));
        if (sysUser != null) {
            String message = "用户已存在";
            return Response.failed(message);
        }
        return Response.ok(saveUser(userDto));
    }

    @Override
    public Response changePassword(UserDTO userDto) {
        UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
        if (Objects.isNull(userVO)) {
            return Response.failed("用户不存在");
        }

        if (StrUtil.isEmpty(userDto.getPassword())) {
            return Response.failed("原密码不能为空");
        }

        if (!ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
            log.info("原密码错误，修改个人信息失败:{}", userDto.getUsername());
            return Response.failed("原密码错误");
        }

        if (StrUtil.isEmpty(userDto.getNewPasswords())) {
            return Response.failed("新密码不能为空");
        }
        String password = ENCODER.encode(userDto.getNewPasswords());

        this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, password)
                .eq(SysUser::getId, userVO.getId()));
        return Response.ok();
    }

    @Override
    public Response checkPassword(String password) {
        String username = SecurityUtils.getUser().getUsername();
        SysUser condition = new SysUser();
        condition.setUsername(username);
        SysUser sysUser = this.getOne(new QueryWrapper<>(condition));

        if (!ENCODER.matches(password, sysUser.getPassword())) {
            log.info("原密码错误");
            return Response.failed("密码输入错误");
        }
        else {
            return Response.ok();
        }
    }
}
