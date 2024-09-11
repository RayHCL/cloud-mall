package com.ray.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ray
 * @description
 * @date 2024/08/17
 */
@ApiModel("管理员查询条件参数对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserQueryParam {
    @ApiModelProperty("管理员名称")
    private String username;
}
