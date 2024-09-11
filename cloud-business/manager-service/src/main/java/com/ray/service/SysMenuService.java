package com.ray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ray.domain.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
*/
public interface SysMenuService extends IService<SysMenu>{

    /**
     * 根据用户ID查询菜单集合
     * @param userId 用户ID
     * @return 菜单集合
     */
    Set<SysMenu> queryUserMenus(Long userId);

    /**
     * 查询系统所有权限集合
     * @return 系统所有权限集合
     */
    List<SysMenu> queryAllSysMenuList();


}
