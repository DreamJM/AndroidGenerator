package com.dream.android.sample.lib.utils;

import android.content.Context;
import android.text.TextUtils;
import com.dream.android.sample.lib.R;

/**
 * Description:error information
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class ErrorUtil {

    public static String getChatErrorMsg(Context context, int code) {
        String faultString = context.getString(R.string.network_error);
        int id = context.getResources().getIdentifier("chat_error_" + Math.abs(code), "string" , context.getPackageName());
        if(id != 0) {
            faultString = context.getString(id);
        }
        return faultString;
    }

    public static String getServerErrorMsg(Context context, int code, String msg) {
        String faultString;
        int id = context.getResources().getIdentifier("MSG_E" + code, "string" , context.getPackageName());
        if(id != 0) {
            faultString = context.getString(id);
        }else{
            faultString = msg;
        }
        if(TextUtils.isEmpty(faultString)){
            faultString = context.getString(R.string.network_error);
        }
        return faultString;
    }

}
