package com.dream.android.sample.lib.data.net.base;

/**
 * Description: Definition of Server Error
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
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
