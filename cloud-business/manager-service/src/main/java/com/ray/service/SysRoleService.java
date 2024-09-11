package com.ray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ray.domain.SysRole;
import com.ray.dto.SysRoleAddOrUpdateParam;
import com.ray.vo.SysRoleDetailVo;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
*/
public interface SysRoleService extends IService<SysRole>{

        /**
         * 查询系统角色列表
         * @return
         */
        List<SysRole> queryAllSysRoleList();


        /**
         * 新增角色
         * @param addOrUpdateParam
         * @return
         */
        Integer addSysRole(SysRoleAddOrUpdateParam addOrUpdateParam);

        /**
         * 查询角色详情(包含权限ID集合)
         * @param roleId 角色标识
         * @return
         */
        SysRoleDetailVo querySysRoleDetailById(Long roleId);

        /**
         * 修改角色信息
         * @param addOrUpdateParam
         * @return
         */
        Integer modifySysRole(SysRoleAddOrUpdateParam addOrUpdateParam);

        /**
         * 批量删除角色
         * @param roleIds 角色ID集合
         * @return
         */
        Integer removeSysRolesByIds(List<Long> roleIds);
}
