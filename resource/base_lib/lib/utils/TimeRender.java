package com.dream.android.sample.lib.utils;

import com.dream.android.sample.lib.base.BaseConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Description: Date Tools
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class TimeRender {

    public static final String TODAY = "";
    public static final String YESTERDAY = "yesterday ";
    public static final String BEFORE_YESTERDAY = "before yesterday ";

    public static String format(Date date) {
        SimpleDateFormat formatBuilder;
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        today.setTime(new Date());
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        target.setTime(date);
        target.set(Calendar.HOUR, 0);
        target.set(Calendar.MINUTE, 0);
        target.set(Calendar.SECOND, 0);
        long intervalMilli = target.getTimeInMillis() - today.getTimeInMillis();
        int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
        String pre = showDateDetail(xcts);
        if (pre == null) {
            formatBuilder = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return formatBuilder.format(date);
        } else {
            formatBuilder = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            return pre + formatBuilder.format(date);
        }
    }

    private static String showDateDetail(int xcts) {
        switch (xcts) {
            case 0:
                return TODAY;
            case -1:
                return YESTERDAY;
            case -2:
                return BEFORE_YESTERDAY;
            default:
                return null;
        }
    }

    public static Date parseGMTDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatToDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    public static String formatStandard(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }
    public static String formatNoYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static String formatToMinute(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static Integer getDivDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            return (int)((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
        } else {
            return null;
        }
    }

    public static String[] getDaysInWeek(Date date) {
        String[] dates = new String[7];
        for(int i = 1; i <=7 ; i++) {
            Date tmpDate = new Date(date.getTime() + i * 24 * 60 * 60 * 1000);
            dates[i - 1] = getDayInWeek(tmpDate);
        }
        return dates;
    }

    public static String getDayInWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 E", Locale.getDefault());
        return sdf.format(date);
    }
}
