package com.ray.dto;

import com.ray.domain.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统角色新增或修改入参对象")
public class SysRoleAddOrUpdateParam extends SysRole {
    @ApiModelProperty("权限ID集合")
    private List<Long> menuIdList;
}