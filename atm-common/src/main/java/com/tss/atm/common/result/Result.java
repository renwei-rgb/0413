package com.tss.atm.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    // 添加一个无参的成功方法
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    // 添加一个带消息的成功方法
    public static <T> Result<T> success(String message) {
        return new Result<>(200, message, null);
    }

    // 添加一个带消息和数据的成功方法
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }
} 