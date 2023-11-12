package com.monousoooo.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "SysDict", description = "字典")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @Schema(name = "dictId", description = "字典id")
    private Long id;

    @Schema(name = "dictName", description = "字典名称")
    private String dictName;

    @Schema(name = "dictCode", description = "字典编码")
    private String dictCode;

    @Schema(name = "dictDesc", description = "字典描述")
    private String dictDesc;

    @Schema(name = "dictType", description = "字典类型")
    private String dictType;
}
