package com.example.homedemo.utils;

/**
 * Created by jingru on 2016/9/30.
 */
public class ValueUtil {
    /**
     * 是否字符串有效
     * @param string
     * @return
     */
    public static boolean isStringValid(String string){
        if (string == null || string.trim().equals("")|| string.trim().equals("null")) {
            return false;
        }
        return true;
    }


}
