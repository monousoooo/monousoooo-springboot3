package com.monousoooo.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "SysDictItem", description = "字典项")
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends Model<SysDictItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @Schema(name = "id", description = "字典项id")
    private Long id;

    @Schema(name = "dictId", description = "字典id")
    private Long dictId;

    @Schema(name="itemValue",description = "字典项值")
    @JsonProperty(value = "value")
    private String itemValue;

    @Schema(name="label",description = "字典项标签")
    private String label;

    @Schema(name="dictType",description = "字典类型")
    private String dictType;

    @Schema(name="description",description = "字典项描述")
    private Integer description;

    @Schema(name="sort",description = "排序")
    private Integer sort;
}
