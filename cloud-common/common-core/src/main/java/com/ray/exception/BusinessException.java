package com.ray.exception;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
