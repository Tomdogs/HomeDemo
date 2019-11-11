package com.example.homedemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tomdog on 2019/10/29.
 */
public class UnicodeToChinese {

    /**
     * unicode编码转换为汉字
     * @param unicodeStr 待转化的编码
     * @return 返回转化后的汉子
     */
    public static String UnicodeToCN(String unicodeStr) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicodeStr);
        char ch;
        while (matcher.find()) {
            //group
            String group = matcher.group(2);
            //ch:'李四'
            ch = (char) Integer.parseInt(group, 16);
            //group1
            String group1 = matcher.group(1);
            unicodeStr = unicodeStr.replace(group1, ch + "");
        }

        return unicodeStr.replace("\\", "").trim();
    }

}
