package com.ray.strategy.impl;

import com.ray.constant.AuthConstants;
import com.ray.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @description 具体的会员登录策略
 * @date 2024/08/04
 */
@Service(AuthConstants.MEMBER_LOGIN)
public class MemberLoginStrategy implements LoginStrategy {
    /**
     * 编写用户购物车系统会员的登录逻辑
     * @param username
     * @return
     */
    @Override
    public UserDetails realLogin(String username) {
        return null;
    }
}
