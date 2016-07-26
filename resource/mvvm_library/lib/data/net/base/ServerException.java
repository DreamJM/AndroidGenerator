package com.wafa.android.pei.lib.data.net.base;

/**
 * Description: Defination of Server Error
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class ServerException extends Exception {

    private int code;

    public ServerException(int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
