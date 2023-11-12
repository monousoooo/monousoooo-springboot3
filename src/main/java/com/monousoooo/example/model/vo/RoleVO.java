package com.monousoooo.example.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RoleVO", description = "角色视图对象")
public class RoleVO {

    private Long roleId;

    private String menuIds;
}
