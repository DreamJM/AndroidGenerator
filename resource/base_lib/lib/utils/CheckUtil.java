package com.dream.android.sample.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class CheckUtil {

    public static final String REGEX_PWD = "^[a-zA-Z0-9_]{6,16}$";

    public static final String REGEX_MOBILE="^1[3|4|5|7|8|][0-9]{9}$";

    public static boolean isEmail(String email) {
        if(email == null) {
            return false;
        }
        Pattern p = Pattern
                .compile("^(.+?)@(.+?)\\.(.+?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPhoneNum(String phone) {
        if (phone == null) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        if (Pattern.matches(REGEX_PWD, password)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
         }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
            (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
            ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
            && (codePoint <= 0x10FFFF));
    }
}
