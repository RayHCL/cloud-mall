package com.ray.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.domain.SysUser;
import com.ray.dto.SysUserAddOrUpdateParam;
import com.ray.dto.SysUserQueryParam;
import com.ray.model.Result;
import com.ray.service.SysUserService;
import com.ray.util.AuthUtils;
import com.ray.vo.SysUserDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
@Api(tags = "管理员接口管理")
@RequestMapping("sys/user")
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("根据用户ID查询用户信息")
    @GetMapping("info")
    public Result<SysUser> loadSysUserInfo() {
        // 获取用户ID
        Long userId = AuthUtils.getLoginUserId();
        // 根据用户ID查询用户信息
        SysUser sysUser = sysUserService.getById(userId);
        return Result.success(sysUser);
    }

    @ApiOperation("多条件分页查询管理员列表")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<Page<SysUser>> loadSysUserPage(Page<SysUser> page, SysUserQueryParam queryParam) {
        page = sysUserService.page(page,new LambdaQueryWrapper<SysUser>()
                .like(StringUtils.hasText(queryParam.getUsername()),SysUser::getUsername,queryParam.getUsername())
                .orderByDesc(SysUser::getCreateTime)
        );
        return Result.success(page);
    }

    @ApiOperation("新增管理员")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> saveSysUser(@RequestBody SysUserAddOrUpdateParam addOrUpdateParam) {
        Integer i = sysUserService.addSysUser(addOrUpdateParam);
        return Result.handle(i>0);
    }

    @ApiOperation("查询管理员详情(包含角色ID)")
    @GetMapping("info/{userId}")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<SysUserDetailVo> loadSysUserDetail(@PathVariable Long userId) {
        // 根据管理员ID查询管理员详情
        SysUserDetailVo sysUserDetailVo = sysUserService.querySysUserDetailByUserId(userId);
        return Result.success(sysUserDetailVo);
    }

    @ApiOperation("修改管理员信息")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> modifySysUser(@RequestBody SysUserAddOrUpdateParam addOrUpdateParam) {
        Integer count = sysUserService.modifySysUser(addOrUpdateParam);
        return Result.handle(count>0);
    }


    @DeleteMapping("{userIds}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> removeSysUsers(@PathVariable List<Long> userIds) {
        Integer count = sysUserService.removeSysUsersByUserIdList(userIds);
        return Result.handle(count==userIds.size());
    }
}
