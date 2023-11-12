package com.monousoooo.example.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "SysUserRole", description = "用户角色")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "roleId", description = "角色id")
    private Long roleId;
}
