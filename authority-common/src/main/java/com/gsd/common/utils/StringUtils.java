package com.gsd.common.utils;

import com.gsd.common.constant.Constants;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static String NULLSTR="";
    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
    public static boolean isEmpty(String str){
        return isNull(str)||NULLSTR.equals(str.trim());
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link)
    {
        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
    }
}
