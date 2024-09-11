package com.ray.config;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.constant.AuthConstants;
import com.ray.constant.BusinessEnum;
import com.ray.constant.HttpConstants;
import com.ray.model.LoginResult;
import com.ray.model.Result;
import com.ray.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.time.Duration;

/**
 * @author Ray
 * @description 认证安全配置类
 * @date 2024/08/04
 */
@Configuration
@Slf4j
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 依赖注入容器中实现UserDetailsServic接口的实现类
     */
    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 自定义的认证流程
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置security框架走自己的登录流程
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 网络请求配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
// 关闭跨站请求伪造的拦截
        http.csrf().disable();
        // 关闭跨域请求
        http.cors().disable();
        // 关闭session策略，不创建session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置登录的信息
        http.formLogin()
                .loginProcessingUrl(AuthConstants.LOGIN_URL)   // 指定登录的请求路径
                .successHandler(authenticationSuccessHandler())   // 登录成功的处理流程
                .failureHandler(authenticationFailureHandler());  // 登录失败的处理流程

        // 配置登出的信息
        http.logout()
                .logoutUrl(AuthConstants.LOGIN_OUT)    // 指定登出的请求路径
                .logoutSuccessHandler(logoutSuccessHandler()); // 登出成功的处理流程

        // 配置其它请求的规则：
        // 所有接口都必须认证通过后才可以访问，也就是说必须在securityContext中有认证对象才可以访问
        http.authorizeHttpRequests()
                .anyRequest().authenticated();  // 其它任何请求都必须认证
    }

    /**
     * 登录成功处理器
     *  1.生成一个token
     *  2.将当前的认证对象存入redis
     *  3.返回token
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 设置响应信息
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            // 使用UUID生成token
            String token = UUID.randomUUID().toString();
            // 从Spring Security中获取用户身份信息，并转换为json字符串
            String userStr = JSON.toJSONString(authentication.getPrincipal());
            // 将用户的信息存放到redis缓存中，并设置token过期时间为4个小时14400秒
            stringRedisTemplate.opsForValue().set(AuthConstants.LOGIN_TOKEN_PREFIX+token,userStr, Duration.ofSeconds(AuthConstants.TOKEN_TIME));
            // 封装登录响应对象
            LoginResult loginResult = new LoginResult(token,AuthConstants.TOKEN_TIME);
            // 封装统一返回结果对象
            Result<LoginResult> result = Result.success(loginResult);
            // 返回结果返
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    /**
     * 登录失败处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            // 设置响应信息
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            // 封装返回结果
            Result<String> result = new Result<>();
            result.setCode(BusinessEnum.UN_AUTHORIZATION.getCode());
            if (exception instanceof BadCredentialsException) {
                result.setMsg("用户名或密码有误");
            } else if (exception instanceof UsernameNotFoundException) {
                result.setMsg("用户名不存在");
            } else if (exception instanceof AccountExpiredException) {
                result.setMsg("帐号异常，请联系管理员");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                result.setMsg(exception.getMessage());
            } else {
                result.setMsg(BusinessEnum.OPERATION_FAIL.getDesc());
            }
            // 返回结果对象
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    /**
     * 登出成功处理器
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication) -> {
            // 设置响应信息
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            // 从请求头中获取token
            String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
            String token = authorization.replaceFirst(AuthConstants.BEARER,"");
            // 删除缓存中的token
            stringRedisTemplate.delete(AuthConstants.LOGIN_TOKEN_PREFIX+token);
            // 返回结果对象
            Result<String> result = Result.success(null);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
