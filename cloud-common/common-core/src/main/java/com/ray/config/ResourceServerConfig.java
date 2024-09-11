package com.ray.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.constant.BusinessEnum;
import com.ray.constant.HttpConstants;
import com.ray.constant.ResourceConstants;
import com.ray.filter.TokenTranslationFilter;
import com.ray.model.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private TokenTranslationFilter tokenTranslationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭跨站请求伪造
        http.csrf().disable();
        // 关闭跨域请求
        http.cors().disable();
        // 关闭session管理
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 编写token解析过滤器，将token转换为用户信息存放到security容器中，放在认证过滤器之前（无需再登录）
        http.addFilterBefore(tokenTranslationFilter, UsernamePasswordAuthenticationFilter.class);
        // 没有携带token，直接访问资源服务器，拦截报401，如果有token但是没有权限访问，报403
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint()) // 处理：没有携带token的请求直接访问资源服务器
                .accessDeniedHandler(accessDeniedHandler()); // 处理：携带token，但是权限不够
        // 配置放行路径
        http.authorizeHttpRequests()
                .antMatchers(ResourceConstants.RESOURCE_ALLOW_URLS) // 放行路径ResourceConstants.RESOURCE_ALLOW_URLS
                .permitAll()
                .anyRequest().authenticated();  // 其它请求都必须认证
    }

    /**
     * 没有携带token处理器，直接返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            // 设置响应头信息
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            // 返回结果对象
            Result<String> result = Result.fail(BusinessEnum.UN_AUTHORIZATION);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(response);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    /**
     * 携带token权限不足处理器，直接返回403
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // 设置响应头信息
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            // 返回结果对象
            Result<String> result = Result.fail(BusinessEnum.ACCESS_DENY_FAIL);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }
}
