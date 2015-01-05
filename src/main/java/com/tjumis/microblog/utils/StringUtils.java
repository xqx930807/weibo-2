package com.tjumis.microblog.utils;

import java.util.List;

/**
 * Created by yong.h on 15/1/5.
 */
public class StringUtils {
    public static String join(String[] arr, String join) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                builder.append(arr[i]);
            } else {
                builder.append(arr[i]).append(join);
            }
        }
        return builder.toString();
    }

    public static String sqlJoin(String[] arr, String join) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                builder.append("\"").append(arr[i]).append("\"");
            } else {
                builder.append("\"").append(arr[i]).append("\"").append(join);
            }
        }
        return builder.toString();
    }

    public static String sqlJoin(List arr, String join) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            if (i == arr.size() - 1) {
                builder.append("\"").append(arr.get(i).toString()).append("\"");
            } else {
                builder.append("\"").append(arr.get(i).toString()).append("\"").append(join);
            }
        }
        return builder.toString();
    }
}
