package com.ray.exception.handler;

import com.ray.constant.BusinessEnum;
import com.ray.exception.BusinessException;
import com.ray.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
@RestControllerAdvice
public class GlobalExceptionHandle {
    /**
     * 处理：业务异步，异常信息可以是用户看到的
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<String> businessHandler(BusinessException e) {
        return Result.fail(BusinessEnum.OPERATION_FAIL.getCode(),e.getMessage());
    }

    /**
     * 处理：服务运行时异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeException(RuntimeException e) {
        return Result.fail(BusinessEnum.SERVER_INNER_ERROR);
    }

    /**
     * 处理：权限不足异常
     * @param e
     * @throws AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 抛出AccessDeniedException异常，走自定义AccessDeniedException异常处理
        throw e;
    }
}
