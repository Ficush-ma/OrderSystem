package com.orderSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static Result<Void> success(){
        return new Result<>(200, "success", null);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(200, "success", data);
    }
    public static Result<Void> error(int code,String msg){
        return new Result<>(code, msg, null);
    }
}
