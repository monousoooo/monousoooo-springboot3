package com.monousoooo.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "SysMenu", description = "菜单")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu>{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @Schema(name = "menuId", description = "菜单id")
    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    @Schema(name = "name", description = "菜单名称")
    private String name;

    @NotBlank(message="菜单父id不能为空")
    @Schema(name = "parentId", description = "菜单父id")
    private Long parentId;

    @Schema(name = "permission", description = "权限标识")
    private String permission;

    @Schema(name = "path", description = "路由地址")
    private String path;

    @Schema(name="icon", description = "图标")
    private String icon;

    @Schema(name="visible", description = "是否可见")
    private String visible;

    @Schema(name="sort", description = "排序")
    private Integer sort;

    @Schema(name="menuType", description = "菜单类型")
    private String menuType;

    @Schema(name="keepAlive", description = "是否缓存")
    private String keepAlive;
}
