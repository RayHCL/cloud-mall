package com.ray.controller;

import com.ray.domain.SysMenu;
import com.ray.model.Result;
import com.ray.service.SysMenuService;
import com.ray.util.AuthUtils;
import com.ray.vo.MenuAndPermsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(tags = "系统权限接口管理")
@RequestMapping("sys/menu")
@RestController
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("查询登录用户的菜单和权限")
    @GetMapping("nav")
    public Result<MenuAndPermsVo> loadUserMenuAndPerms() {
        // 获取当前登录用户ID
        Long userId = AuthUtils.getLoginUserId();
        // 查询当前登录用户的权限集合
        Set<String> perms = AuthUtils.getPerms();
        // 根据用户ID查询菜单集合
        Set<SysMenu> menus = sysMenuService.queryUserMenus(userId);
        // 创建菜单和权限集合对象
        MenuAndPermsVo menuAndPermsVo = new MenuAndPermsVo(menus,perms);
        return Result.success(menuAndPermsVo);
    }


    @ApiOperation("查询系统所有权限集合")
    @GetMapping("table")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenu>> loadAllSysMenuList() {
        List<SysMenu> menuList = sysMenuService.queryAllSysMenuList();
        return Result.success(menuList);
    }

    @ApiOperation("新增权限")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> saveSysMenu(@RequestBody SysMenu sysMenu) {
        return Result.handle(sysMenuService.save(sysMenu));
    }

    @ApiOperation("查询权限详情")
    @GetMapping("info/{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenu> loadSysMenuInfo(@PathVariable Long menuId) {
        SysMenu menu = sysMenuService.getById(menuId);
        return Result.success(menu);
    }

    @ApiOperation("修改权限")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> modifySysMenu(@RequestBody SysMenu sysMenu) {
        return Result.handle(sysMenuService.updateById(sysMenu));
    }

    @ApiOperation("删除权限")
    @DeleteMapping("{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> removeSysMenu(@PathVariable Long menuId) {
        return Result.handle(sysMenuService.removeById(menuId));
    }

}