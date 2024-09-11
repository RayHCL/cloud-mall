package com.ray.vo;

import com.ray.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
@ApiModel("菜单和权限集合")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuAndPermsVo {
    @ApiModelProperty("菜单集合")
    private Set<SysMenu> menuList;

    @ApiModelProperty("权限集合")
    private Set<String> authorities;
}
