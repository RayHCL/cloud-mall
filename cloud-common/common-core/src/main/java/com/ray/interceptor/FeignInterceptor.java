package com.ray.interceptor;

import com.ray.constant.AuthConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前请求的上下文
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取当前请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        // 将当前请求对象头里的token传递到下一个请求对象的请求头中
        requestTemplate.header(AuthConstants.AUTHORIZATION,request.getHeader(AuthConstants.AUTHORIZATION));
    }
}
