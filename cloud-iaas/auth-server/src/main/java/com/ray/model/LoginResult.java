package com.ray.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ray
 * @description 统一登录返回结果对象
 * @date 2024/08/04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    /**
     * token令牌
     */
    private String accessToken;

    /**
     * 过期时间（单位：秒）
     */
    private Long expireIn;
}
