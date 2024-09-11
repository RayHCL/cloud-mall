package com.ray.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.domain.SysRole;
import com.ray.dto.SysRoleAddOrUpdateParam;
import com.ray.dto.SysRoleQueryParam;
import com.ray.model.Result;
import com.ray.service.SysRoleService;
import com.ray.vo.SysRoleDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "系统角色接口管理")
@RequestMapping("sys/role")
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询系统角色列表")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result<List<SysRole>> loadSysRoleList() {
        List<SysRole> roles = sysRoleService.queryAllSysRoleList();
        return Result.success(roles);
    }

    @ApiOperation("多条件分页查询角色列表")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result<Page<SysRole>> loadSysRolePage(Page<SysRole> page, SysRoleQueryParam queryParam) {
        page = sysRoleService.page(page,new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.hasText(queryParam.getRoleName()),SysRole::getRoleName,queryParam.getRoleName())
                .orderByDesc(SysRole::getCreateTime)
        );
        return Result.success(page);
    }


    @ApiOperation("新增角色")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result<String> saveSysRole(@RequestBody SysRoleAddOrUpdateParam addOrUpdateParam) {
        Integer count = sysRoleService.addSysRole(addOrUpdateParam);
        return Result.handle(count>0);
    }

    @ApiOperation("查询角色详情(包含权限ID集合)")
    @GetMapping("info/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result<SysRoleDetailVo> loadSysRoleDetailInfo(@PathVariable Long roleId) {
        SysRoleDetailVo roleDetailVo = sysRoleService.querySysRoleDetailById(roleId);
        return Result.success(roleDetailVo);
    }

    @ApiOperation("修改角色信息")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> modifySysRole(@RequestBody SysRoleAddOrUpdateParam addOrUpdateParam) {
        Integer count = sysRoleService.modifySysRole(addOrUpdateParam);
        return Result.handle(count>0);
    }


    @ApiOperation("批量删除角色")
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result<String> removeSysRoles(@RequestBody List<Long> roleIds) {
        Integer count = sysRoleService.removeSysRolesByIds(roleIds);
        return Result.handle(count==roleIds.size());
    }
}