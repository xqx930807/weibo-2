package com.tjumis.microblog.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yong.h on 15/1/5.
 */
public class TimeUtils {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format() {
        return format(new Date(), formatter);
    }

    public static String format(Date time) {
        return format(time, formatter);
    }

    public static String format(Date time, DateFormat fomatter) {
        return fomatter.format(time);
    }
}
