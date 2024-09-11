package com.ray.service.impl;

import com.ray.constant.AuthConstants;
import com.ray.factory.LoginStrategyFactory;
import com.ray.strategy.LoginStrategy;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ray
 * @description
 * @date 2024/08/04
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private LoginStrategyFactory loginStrategyFactory;

    /**
     * Spring Security框架核心业务的拓展
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 从请求头中获取请求的登录类型
        String loginType = request.getHeader(AuthConstants.LOGIN_TYPE);
        // 判断登录类型是否有值
        if (!StringUtils.hasText(loginType)) {
            // 登录类型不存在，抛出异常
            throw new InternalAuthenticationServiceException("非法登录，登录类型不匹配");
        }
        // 使用策略模式，实现不同情况的处理方案
        LoginStrategy instance = loginStrategyFactory.getInstance(loginType);
        if (ObjectUtils.isEmpty(instance)) {
            throw new InternalAuthenticationServiceException("非法登录，登录类型不匹配");
        }
        return instance.realLogin(username);
    }
}
