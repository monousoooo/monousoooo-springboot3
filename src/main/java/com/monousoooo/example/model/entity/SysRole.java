package com.monousoooo.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "SysRole", description = "系统角色")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @Schema(name = "roleId", description = "角色编号")
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @Schema(name = "roleName", description = "角色名称")
    private String roleName;

    @NotBlank(message = "角色标识不能为空")
    @Schema(name = "roleCode", description = "角色标识")
    private String roleCode;

    @Schema(name = "roleDesc", description = "角色描述")
    private String roleDesc;

}
