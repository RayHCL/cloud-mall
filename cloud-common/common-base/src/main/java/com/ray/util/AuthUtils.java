package com.ray.util;

import com.ray.model.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
public class AuthUtils {
    /**
     * 获取Security容器中的认证用户对象
     * @return
     */
    public static SecurityUser getLoginUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取登录用户ID
     * @return
     */
    public static Long getLoginUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取登录用户的权限集合
     * @return
     */
    public static Set<String> getPerms() {
        return getLoginUser().getPerms();
    }
}
