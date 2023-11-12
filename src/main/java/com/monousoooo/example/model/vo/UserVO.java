package com.monousoooo.example.model.vo;

import com.monousoooo.example.model.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "UserVO", description = "用户视图对象")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(name = "id", description = "用户id")
    private Long id;

    @Schema(name = "username", description = "用户名")
    private String username;

    @Schema(name = "password", description = "密码")
    private String password;

    @Schema(name = "wxOpenid", description = "微信openid")
    private String wxOpenid;

    @Schema(name = "phone", description = "手机号")
    private String phone;

    @Schema(name = "avatar", description = "头像")
    private String avatar;

    @Schema(name = "roleList", description = "角色列表")
    private List<SysRole> roleList;

    @Schema(name = "nickname", description = "昵称")
    private String nickname;
}
