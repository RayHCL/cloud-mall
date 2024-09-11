package com.ray.factory;

import com.ray.strategy.LoginStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ray
 * @description 登录策略工厂类
 * @date 2024/08/04
 */
@Component
public class LoginStrategyFactory {
    /**
     * 通过依赖注入获取spring容器中的登录策略
     */
    @Resource
    private Map<String, LoginStrategy> loginStrategyMap = new HashMap<>();

    /**
     * 根据登录类型返回不同的登录策略
     * @param loginType
     * @return
     */
    public LoginStrategy getInstance(String loginType) {
        return loginStrategyMap.get(loginType);
    }
}
