package com.wafa.android.pei.lib.model;

/**
 * Description：服务器返回信息的公共部分
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class ServerResult<T> {

    public static final int SUCCESS = 0;

    /**
     * 服务器返回码
     */
    private int code;

    /**
     * 服务器返回信息
     */
    private String msg;

    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return SUCCESS == code;
    }
}
