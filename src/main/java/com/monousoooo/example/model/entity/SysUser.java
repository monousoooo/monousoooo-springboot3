package com.monousoooo.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "SysUser", description = "系统用户")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(name = "userId", description = "用户id")
    private Long id;

    /**
     * 用户名
     */
    @Schema(name = "username", description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(name = "password", description = "密码")
    private String password;

    /**
     * 手机号
     */
    @Schema(name = "phone", description = "手机号")
    private String phone;

    /**
     * 头像
     */
    @Schema(name = "avatar", description = "头像")
    private String avatar;

    /**
     * 部门id
     */
    @Schema(name = "deptId", description = "部门id")
    private String deptId;

    /**
     * 微信openid
     */
    @Schema(name = "wxOpenid", description = "微信openid")
    private String wxOpenid;

    /**
     * 微信小程序openid
     */
    @Schema(name = "miniOpenid", description = "微信小程序openid")
    private String miniOpenid;

    /**
     * 昵称
     */
    @Schema(name = "nickname", description = "昵称")
    private String nickname;
}
