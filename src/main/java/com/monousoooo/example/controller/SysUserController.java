package com.monousoooo.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monousoooo.example.constant.CommonConstants;
import com.monousoooo.example.model.dto.UserDTO;
import com.monousoooo.example.model.entity.SysUser;
import com.monousoooo.example.security.util.SecurityUtils;
import com.monousoooo.example.service.SysUserService;
import com.monousoooo.example.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理")
public class SysUserController {

    private final SysUserService userService;

    @GetMapping(value = { "/info/query" })
    public Response info(@RequestParam(required = false) String username, @RequestParam(required = false) String phone) {
        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda()
                .eq(StrUtil.isNotBlank(username), SysUser::getUsername, username)
                .eq(StrUtil.isNotBlank(phone), SysUser::getPhone, phone));
        if (user == null) {
            return Response.failed("用户不存在");
        }
        return Response.ok(userService.findUserInfo(user));
    }

    @GetMapping(value = { "/info" })
    public Response info() {
        String username = SecurityUtils.getUser().getUsername();
        SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return Response.failed("用户不存在");
        }
        return Response.ok(userService.findUserInfo(user));
    }

    @GetMapping("/details/{id}")
    public Response user(@PathVariable Long id) {
        return Response.ok(userService.selectUserVoById(id));
    }

    @GetMapping("/details")
    public Response getDetails(@ParameterObject SysUser query) {
        SysUser sysUser = userService.getOne(Wrappers.query(query), false);
        return Response.ok(sysUser == null ? null : CommonConstants.SUCCESS);
    }

    @DeleteMapping
    public Response userDel(@RequestBody Long[] ids) {
        return Response.ok(userService.deleteUserByIds(ids));
    }

    @PostMapping
    public Response user(@RequestBody UserDTO userDto) {
        return Response.ok(userService.saveUser(userDto));
    }

    @PutMapping
    public Response updateUser(@Valid @RequestBody UserDTO userDto) {
        return Response.ok(userService.updateUser(userDto));
    }

    @GetMapping("/page")
    public Response getUserPage(@ParameterObject Page page, @ParameterObject UserDTO userDTO) {
        return Response.ok(userService.getUsersWithRolePage(page, userDTO));
    }

    @PutMapping("/edit")
    public Response updateUserInfo(@Valid @RequestBody UserDTO userDto) {
        return userService.updateUserInfo(userDto);
    }

    @PutMapping("/password")
    public Response password(@RequestBody UserDTO userDto) {
        String username = SecurityUtils.getUser().getUsername();
        userDto.setUsername(username);
        return userService.changePassword(userDto);
    }

    @PostMapping("/check")
    public Response check(String password) {
        return userService.checkPassword(password);
    }

}
