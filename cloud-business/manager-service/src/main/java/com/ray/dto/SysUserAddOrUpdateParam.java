package com.ray.dto;

import com.ray.domain.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/08/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("管理员新增或修改入参对象")
public class SysUserAddOrUpdateParam extends SysUser {
    @ApiModelProperty("角色id集合")
    private List<Long> roleIdList;
}
