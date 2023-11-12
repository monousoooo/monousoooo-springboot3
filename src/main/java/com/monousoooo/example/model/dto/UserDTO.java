package com.monousoooo.example.model.dto;

import com.monousoooo.example.model.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Schema(name = "UserDTO", description = "用户数据传输对象")
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {

    @Schema(name = "role", description = "角色")
    private List<Long> role;

    @Schema(name = "newPasswords", description = "新密码")
    private String newPasswords;
}
