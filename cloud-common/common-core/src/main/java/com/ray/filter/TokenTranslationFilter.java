package com.ray.filter;

import com.alibaba.fastjson.JSON;
import com.ray.constant.AuthConstants;
import com.ray.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
@Component
public class TokenTranslationFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * token解析过滤器：
     *  只处理携带token的请求，将用户信息转换出来
     *  没有携带token的请求，交给security框架处理，如果放行则直接访问，
     *    如果没有放行，则交给security的AuthenticationEntryPoint处理
     *  1.获取token值
     *  2.如果有，token转换并处理token续约
     *  3.存放到security上下文中
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的Authorization值
        String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
        // 判断是否有值
        if (StringUtils.hasText(authorization)) {
            // 获取token值
            String token = authorization.replaceFirst(AuthConstants.BEARER, "");
            // 判断token是否有值
            if (StringUtils.hasText(token)) {
                // 处理token续约
                // 从redis中获取token的有效时长
                Long expire = stringRedisTemplate.getExpire(AuthConstants.LOGIN_TOKEN_PREFIX + token, TimeUnit.SECONDS);
                // 判断是否小于过期阈值
                if (expire < AuthConstants.TOKEN_EXPIRE_THRESHOLD_TIME) {
                    // token续约
                    stringRedisTemplate.expire(AuthConstants.LOGIN_TOKEN_PREFIX + token, Duration.ofSeconds(AuthConstants.TOKEN_TIME));
                }

                // token转换为用户信息
                // 从redis中获取用户信息
                String userStr = stringRedisTemplate.opsForValue().get(AuthConstants.LOGIN_TOKEN_PREFIX + token);
                // 将json格式字符串的用户信息转换为SecurityUser对象
                SecurityUser securityUser = JSON.parseObject(userStr, SecurityUser.class);
                // 处理权限
                Set<SimpleGrantedAuthority> grantedAuthorities = securityUser.getPerms()
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
                // 创建security框架认识的用户对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser,null,grantedAuthorities);
                // 将security框架认识的用户对象存放到security上下文中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
