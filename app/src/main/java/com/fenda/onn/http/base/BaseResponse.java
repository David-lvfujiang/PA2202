package com.fenda.onn.http.base;

/**
 * Date : 2020/3/4
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class BaseResponse<T>  {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}