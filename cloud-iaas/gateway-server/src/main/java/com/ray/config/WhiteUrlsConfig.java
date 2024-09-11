package com.ray.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ray
 * @description @RefreshScope：是Spring cloud提供的注解，该注解的主要作用是将带有@RefreshScope的Bean可以在运行时动态刷新，任何使用它们的组件都将在下一个方法调用中获得一个全新的完全初始化的实例
 * @date 2024/08/04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "gateway.white")
@RefreshScope
public class WhiteUrlsConfig {
    /**
     * 白名单地址
     */
    private List<String> allowUrls;
}
