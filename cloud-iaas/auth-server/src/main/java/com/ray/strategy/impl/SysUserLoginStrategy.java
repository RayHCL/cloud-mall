package com.ray.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ray.constant.AuthConstants;
import com.ray.domain.LoginSysUser;
import com.ray.mapper.LoginSysUserMapper;
import com.ray.model.SecurityUser;
import com.ray.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * @author Ray
 * @description 具体的管理员登录策略
 * @date 2024/08/04
 */
@Service(AuthConstants.SYS_USER_LOGIN)
public class SysUserLoginStrategy implements LoginStrategy {
    @Autowired
    private LoginSysUserMapper loginSysUserMapper;
    /**
     * 编写后台管理系统用户的登录逻辑
     * @param username
     * @return
     */
    @Override
    public UserDetails realLogin(String username) {
        // 根据用户名查询用户信息
        LoginSysUser loginSysUser = loginSysUserMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>()
                .eq(LoginSysUser::getUsername, username)
        );
        // 判断用户是否存在
        if (!ObjectUtils.isEmpty(loginSysUser)) {
            SecurityUser securityUser = new SecurityUser();
            // 用户存在，查询用户的权限
            Set<String> perms = loginSysUserMapper.selectPermsBySysUserId(loginSysUser.getUserId());
            securityUser.setUserId(loginSysUser.getUserId());
            securityUser.setUsername(loginSysUser.getUsername());
            securityUser.setPassword(loginSysUser.getPassword());
            securityUser.setLoginType(AuthConstants.SYS_USER_LOGIN);
            securityUser.setShopId(loginSysUser.getShopId());
            securityUser.setStatus(loginSysUser.getStatus());
            // 判断用户是否有权限
            if (!CollectionUtils.isEmpty(perms) && perms.size() != 0) {
                securityUser.setPerms(perms);
            }
            return securityUser;
        }
        return null;
    }
}
