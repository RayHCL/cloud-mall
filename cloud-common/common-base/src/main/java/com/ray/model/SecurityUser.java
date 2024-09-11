package com.ray.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ray
 * @description Spring Security安全框架中能够识别的安全用户对象
 * @date 2024/08/04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements UserDetails {
    /**
     * 用户标识
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录类型
     */
    private String loginType;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 用户所属店铺标识
     */
    private Long shopId;
    /**
     * 用户权限
     */
    private Set<String> perms = new HashSet<>();
    // TODO 用户购物车系统用户属性还未添加

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 在Spring Security框架中用户的唯一身份，不能重复
     * @return
     */
    @Override
    public String getUsername() {
        return this.loginType + this.userId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }

    /**
     * 处理用户权限记录中包含多个权限的情况
     * @param perms
     */
    public void setPerms(Set<String> perms) {
        HashSet<String> finalPermsSet = new HashSet<>();
        perms.forEach(perm -> {
            // 判断权限记录是否包含","号
            if (perm.contains(",")) {
                // 根据","号分隔
                String[] realPerms = perm.split(",");
                // 循环遍历获取每一个权限，存放到最终权限集合中
                for (String realPerm : realPerms) {
                    finalPermsSet.add(realPerm);
                }
            } else {
                finalPermsSet.add(perm);
            }
        });
        this.perms = finalPermsSet;
    }
}
