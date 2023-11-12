package com.monousoooo.example.model.dto;

import com.monousoooo.example.model.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "UserInfo", description = "用户信息")
public class UserInfo implements Serializable {

    @Schema(name = "sysUser", description = "系统用户")
    private SysUser sysUser;

    @Schema(name = "permissions", description = "权限")
    private String[] permissions;

    @Schema(name = "roles", description = "角色")
    private Long[] roles;
}
