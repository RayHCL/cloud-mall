package com.ray.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.constant.SysRoleConstants;
import com.ray.domain.SysRole;
import com.ray.domain.SysRoleMenu;
import com.ray.dto.SysRoleAddOrUpdateParam;
import com.ray.mapper.SysRoleMapper;
import com.ray.mapper.SysRoleMenuMapper;
import com.ray.service.SysRoleMenuService;
import com.ray.service.SysRoleService;
import com.ray.util.AuthUtils;
import com.ray.vo.SysRoleDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ray.constant.SysRoleConstants.ALL_ROLES;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
*/
@Service
@CacheConfig(cacheNames = "com.ray.service.impl.SysRoleServiceImpl")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Cacheable(key = ALL_ROLES)
    public List<SysRole> queryAllSysRoleList() {
        return sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                .orderByDesc(SysRole::getCreateTime)
        );
    }



    @Override
    @CacheEvict(key = SysRoleConstants.ALL_ROLES)
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysRole(SysRoleAddOrUpdateParam addOrUpdateParam) {
        // 获取登录用户ID
        Long createUserId = AuthUtils.getLoginUserId();
        // 补充角色信息
        addOrUpdateParam.setCreateTime(new Date());
        addOrUpdateParam.setCreateUserId(createUserId);
        // 新增角色
        int count = sysRoleMapper.insert(addOrUpdateParam);
        // 判断是否成功
        if (count > 0) {
            // 获取角色id
            Long roleId = addOrUpdateParam.getRoleId();
            // 获取权限id集合
            List<Long> menuIdList = addOrUpdateParam.getMenuIdList();
            // 判断权限id集合是否有值
            if (!CollectionUtils.isEmpty(menuIdList) && menuIdList.size() != 0) {
                // 创建角色与权限关系对象集合
                List<SysRoleMenu> roleMenuList = new ArrayList<>();
                // 循环遍历权限id集合
                menuIdList.forEach(menuId -> {
                    // 创建角色与权限关系对象
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menuId);
                    // 将角色与权限关系记录添加到集合中
                    roleMenuList.add(roleMenu);
                });
                // 批量添加角色与权限关系记录
                sysRoleMenuService.saveBatch(roleMenuList);
            }
        }
        return count;
    }


    @Override
    public SysRoleDetailVo querySysRoleDetailById(Long roleId) {
        SysRoleDetailVo roleDetailVo = new SysRoleDetailVo();
        // 根据标识查询角色信息
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        // 将角色属性值copy到角色详情对象
        BeanUtils.copyProperties(sysRole,roleDetailVo);
        // 根据角色标识查询角色与权限关系集合
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId)
        );
        // 判断角色与权限关系集合是否有值
        if (!CollectionUtils.isEmpty(roleMenuList) && roleMenuList.size() != 0) {
            // 从角色与权限关系对象集合中获取权限id集合
            List<Long> menuIdList = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            roleDetailVo.setMenuIdList(menuIdList);
        }
        return roleDetailVo;
    }

    @Override
    @CacheEvict(key = SysRoleConstants.ALL_ROLES)
    @Transactional(rollbackFor = Exception.class)
    public Integer modifySysRole(SysRoleAddOrUpdateParam addOrUpdateParam) {
        // 获取角色id
        Long roleId = addOrUpdateParam.getRoleId();
        // 删除角色原有的权限id
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId,roleId)
        );
        // 获取角色新的权限id集合
        List<Long> menuIdList = addOrUpdateParam.getMenuIdList();
        // 判断权限id集合是否有值
        if (!CollectionUtils.isEmpty(menuIdList) && menuIdList.size() != 0) {
            // 创建角色与权限关系对象集合
            List<SysRoleMenu> roleMenuList = new ArrayList<>();
            // 循环遍历权限id集合
            menuIdList.forEach(menuId -> {
                // 创建角色与权限关系对象
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                // 将角色与权限关系记录添加到集合中
                roleMenuList.add(roleMenu);
            });
            // 批量添加角色与权限关系记录
            sysRoleMenuService.saveBatch(roleMenuList);
        }
        // 修改角色信息
        return sysRoleMapper.updateById(addOrUpdateParam);
    }

    @Override
    @CacheEvict(key = SysRoleConstants.ALL_ROLES)
    @Transactional(rollbackFor = Exception.class)
    public Integer removeSysRolesByIds(List<Long> roleIds) {
        // 删除角色原有的权限
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .in(SysRoleMenu::getRoleId,roleIds)
        );
        // 批量删除角色
        return sysRoleMapper.deleteBatchIds(roleIds);
    }

}
