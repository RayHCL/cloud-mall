package com.ray.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ray.domain.SysMenu;

import java.util.Set;

/** 
 * @author Ray
 * @description
 * @date 2024/08/11
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户ID查询菜单集合
     * @param userId
     * @return
     */
    Set<SysMenu> selectUserMenusByUserId(Long userId);
}