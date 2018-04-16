package com.tianyi.util;

/**
 * Created by gaozhilai on 2018/4/4.
 */
public class StringUtil {
    public static boolean isStringEmpty(String str){
        return str==null||"".equals(str)||"undefined".equals(str)?true:false;
    }
}
