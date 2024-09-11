package com.ray.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
@ApiModel("系统角色查询条件参数对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleQueryParam {
    @ApiModelProperty("角色名称")
    private String roleName;
}