package com.ray.strategy;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Ray
 * @description 登录策略接口
 * @date 2024/08/04
 */
public interface LoginStrategy {
    /**
     * 真正的登录处理方法
     * @param username
     * @return
     */
    UserDetails realLogin(String username);
}
