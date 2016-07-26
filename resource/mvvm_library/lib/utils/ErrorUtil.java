package com.wafa.android.pei.lib.utils;

import android.content.Context;
import android.text.TextUtils;
import com.wafa.android.pei.lib.R;

/**
 * Description:服务器错误码与错误信息的转换工具类
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
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
        String faultString = context.getString(R.string.network_error);
        int id = context.getResources().getIdentifier("MSG_E" + code, "string" , context.getPackageName());
        if(id != 0) {
            faultString = context.getString(id);
        }else{
            faultString=msg;
        }
        if(TextUtils.isEmpty(faultString)){
            faultString = context.getString(R.string.network_error);
        }
        return faultString;
    }

}
