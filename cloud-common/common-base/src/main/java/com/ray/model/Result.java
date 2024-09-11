package com.ray.model;

import com.ray.constant.BusinessEnum;

import java.io.Serializable;

/**
 * @author Ray
 * @description 统一规定响应数据
 * @date 2024/08/04
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;
    public Result() {}
    public Result(int code, String msg, T data) {}

    /**
     * 操作成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    /**
     * 操作失败
     * @param businessEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(BusinessEnum businessEnum) {
        Result<T> result = new Result<>();
        result.setCode(businessEnum.getCode());
        result.setMsg(businessEnum.getDesc());
        return result;
    }

    /**
     * 操作失败
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(Integer code,String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    /**
     * 业务处理结果
     * @param flag
     * @return
     */
    public static Result<String> handle(Boolean flag) {
        // 判断是否处理成功
        if (flag) {
            // 业务处理成功
            return success(null);
        }
        // 业务处理失败，返回失败信息
        return fail(BusinessEnum.OPERATION_FAIL);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
