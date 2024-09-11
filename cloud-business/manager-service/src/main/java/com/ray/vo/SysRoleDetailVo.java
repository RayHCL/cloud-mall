package com.ray.vo;

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
@ApiModel("角色详情对象(包含权限ID)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleDetailVo extends SysRole {
    @ApiModelProperty("权限ID集合")
    private List<Long> menuIdList;
}