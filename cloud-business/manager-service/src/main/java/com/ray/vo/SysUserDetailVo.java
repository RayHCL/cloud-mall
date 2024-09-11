package com.ray.vo;

import com.ray.domain.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("管理员详情(包含角色ID)对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDetailVo extends SysUser {
    @ApiModelProperty("角色ID集合")
    private List<Long> roleIdList;
}