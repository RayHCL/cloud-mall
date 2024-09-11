package com.ray.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.config.WhiteUrlsConfig;
import com.ray.constant.AuthConstants;
import com.ray.constant.BusinessEnum;
import com.ray.constant.HttpConstants;
import com.ray.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Ray
 * @description
 * @date 2024/08/04
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    private WhiteUrlsConfig whiteUrlsConfig;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求路径
        String path = request.getPath().toString();
        // 判断请求是否在白名单
        if (whiteUrlsConfig.getAllowUrls().contains(path)) {
            // 请求路径在白名单中，不需要校验，直接放行
            return chain.filter(exchange);
        }
        // 请求路径不在白名单中，需要对其身份进行校验
        // 获取请求头中的Authorization的值
        String authorizationValue = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION);
        // 判断请求头中的Authorization是否有值
        if (StringUtils.hasText(authorizationValue)) {
            // 从Authorization值中获取token（Authorization值的格式：bearer token）
            String token = authorizationValue.replaceFirst(AuthConstants.BEARER, "");
            // 判断token是否有值且token是否有效
            if (StringUtils.hasText(token) && stringRedisTemplate.hasKey(AuthConstants.LOGIN_TOKEN_PREFIX+token)) {
                // token存在且有效，请求放行
                return chain.filter(exchange);
            }
        }
        // 打印 拦截非法请求日志
        log.error("拦截非法请求，拦截时间:{}，请求api接口:{}",new Date(),path);

        // 拦截非法请求，响应消息
        // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 设置响应头，响应内容格式为json数据格式
        response.getHeaders().set(HttpConstants.CONTENT_TYPE,HttpConstants.APPLICATION_JSON);
        // 创建响应结果，由于请求路径的请求头中未携带token即用户未授权响应401状态码
        Result<Object> result = Result.fail(BusinessEnum.UN_AUTHORIZATION);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            //writeValueAsBytes方法表示把参数result转换为json序列，并将结果输出成字节数组。
            bytes = objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return -5;
    }
}
