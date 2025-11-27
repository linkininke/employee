package com.example.employee.common;

import lombok.Data;

/**
 * 统一响应结果类
 */
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * 成功（无数据）
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败（默认状态码）
     */
    public static <T> Result<T> fail(String message) {
        return fail(500, message);
    }

}