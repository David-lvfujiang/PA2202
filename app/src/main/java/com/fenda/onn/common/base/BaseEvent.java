package com.fenda.onn.common.base;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:16
 * desc
 */
public class BaseEvent<T> {
    private int code;
    private String msg;
    private T data;

    public BaseEvent(int code) {
        this.code = code;
    }

    public BaseEvent(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
