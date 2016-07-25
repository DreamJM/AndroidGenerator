package com.wafa.android.pei.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:输入框条件检测工具类
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class CheckUtil {

    public static final String REGEX_PWD = "^[a-zA-Z0-9_]{6,16}$";

    public static final String REGEX_MOBILE="^1[3|4|5|7|8|][0-9]{9}$";

    public static final String REGEX_PRICE = "^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$";

    /**
     * 检查邮件地址
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if(email == null) {
            return false;
        }
        Pattern p = Pattern
                .compile("^(.+?)@(.+?)\\.(.+?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     * 检测手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNum(String phone) {
        if (phone == null) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 检测价格
     *
     * @param price
     * @return
     */
    public static boolean isPrice(String price) {
        if (price == null) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_PRICE);
        Matcher m = p.matcher(price);
        return m.matches();
    }

    /**
     * 检测密码
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        if (Pattern.matches("^[a-zA-Z0-9_]{6,16}$", password)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测QQ
     *
     * @param qq
     * @return
     */
    public static boolean isValidQQ(String qq) {
        if (qq == null) {
            return false;
        }
        if (Pattern.matches("^[0-9]{5,12}$", qq)) {
            return true;
        } else {
            return false;
        }
    }

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        return htmlStr;
    }

    public static String null2String(String info) {
        if(info == null) return "";
        else return info;
    }

    /**
     * 检测是否有emoji表情
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
         }
        return false;
    }
    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
            (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
            ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
            && (codePoint <= 0x10FFFF));
    }
}
