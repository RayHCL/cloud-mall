package com.ray.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.domain.SysUser;
import com.ray.domain.SysUserRole;
import com.ray.dto.SysUserAddOrUpdateParam;
import com.ray.mapper.SysUserMapper;
import com.ray.mapper.SysUserRoleMapper;
import com.ray.service.SysUserRoleService;
import com.ray.service.SysUserService;
import com.ray.util.AuthUtils;
import com.ray.vo.SysUserDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysUser(SysUserAddOrUpdateParam addOrUpdateParam) {
        // 获取登录用户的ID
        Long createUserId = AuthUtils.getLoginUserId();
        // 补充管理员对象属性的值
        addOrUpdateParam.setCreateUserId(createUserId);
        addOrUpdateParam.setCreateTime(new Date());
        addOrUpdateParam.setStatus(1);
        // 默认平台统一一个店铺
        addOrUpdateParam.setShopId(1L);
        // 获取管理员密码
        String password = addOrUpdateParam.getPassword();
        // 判断是否有值
        if (StringUtils.hasText(password)) {
            // 管理员密码使用 加密
            addOrUpdateParam.setPassword(passwordEncoder.encode(password));
        }
        // 新增管理员
        int count = sysUserMapper.insert(addOrUpdateParam);
        if (count > 0) {
            // 获取新增管理员id
            Long userId = addOrUpdateParam.getUserId();
            // 获取管理员角色id集合
            List<Long> roleIdList = addOrUpdateParam.getRoleIdList();
            // 判断角色id集合是否有值
            if (!CollectionUtils.isEmpty(roleIdList) && roleIdList.size() != 0) {
                // 创建管理员与角色关系集合对象
                List<SysUserRole> sysUserRoleList = new ArrayList<>();
                // 循环遍历角色id集合
                roleIdList.forEach(roleId -> {
                    // 创建管理员与角色关系对象
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(userId);
                    sysUserRole.setRoleId(roleId);
                    // 将管理员与角色关系对象添加到管理员与角色关系集合中
                    sysUserRoleList.add(sysUserRole);
                });
                // 批量添加 管理员与角色关系集合
                sysUserRoleService.saveBatch(sysUserRoleList);
            }
        }
        return count;
    }


    @Override
    public SysUserDetailVo querySysUserDetailByUserId(Long userId) {
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        // 根据管理员ID查询管理员详情
        SysUser sysUser = sysUserMapper.selectById(userId);
        // 根据管理员ID查询管理员与角色关系集合
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId)
        );
        // 判断管理员与角色关系集合是否有值
        if (!CollectionUtils.isEmpty(sysUserRoleList) && sysUserRoleList.size() != 0) {
            // 从管理员与角色关系集合中获取角色ID集合
            List<Long> roleIdList = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            sysUserDetailVo.setRoleIdList(roleIdList);
        }
        // 将sysUser对象的属性值copy到sysUserDetailVo对象中
        BeanUtils.copyProperties(sysUser,sysUserDetailVo);
        return sysUserDetailVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer modifySysUser(SysUserAddOrUpdateParam addOrUpdateParam) {
        // 获取管理员ID
        Long userId = addOrUpdateParam.getUserId();
        // 删除管理员原有的角色
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId,userId)
        );
        // 获取管理员角色id集合
        List<Long> roleIdList = addOrUpdateParam.getRoleIdList();
        // 判断角色id集合是否有值
        if (!CollectionUtils.isEmpty(roleIdList) && roleIdList.size() != 0) {
            // 创建管理员与角色关系集合对象
            List<SysUserRole> sysUserRoleList = new ArrayList<>();
            // 循环遍历角色id集合
            roleIdList.forEach(roleId -> {
                // 创建管理员与角色关系对象
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                // 将管理员与角色关系对象添加到管理员与角色关系集合中
                sysUserRoleList.add(sysUserRole);
            });
            // 批量添加 管理员与角色关系集合
            sysUserRoleService.saveBatch(sysUserRoleList);
        }
        // 获取管理员新密码
        String newPassword = addOrUpdateParam.getPassword();
        // 判断新密码是否有值，如果有值：设置新密码，相反：密码不变
        if (StringUtils.hasText(newPassword)) {
            // 新密码加密
            String encodeNewPassword = passwordEncoder.encode(newPassword);
            addOrUpdateParam.setPassword(encodeNewPassword);
        }
        return sysUserMapper.updateById(addOrUpdateParam);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer removeSysUsersByUserIdList(List<Long> userIds) {
        // 批量删除指定管理员与角色的关系记录
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .in(SysUserRole::getUserId,userIds)
        );
        // 批量删除指定管理员
        return sysUserMapper.deleteBatchIds(userIds);
    }
}
