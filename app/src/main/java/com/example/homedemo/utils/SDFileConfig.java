package com.example.homedemo.utils;

import android.os.Environment;

/**
 * SD卡中的文件相关工具类
 * Created by jingru on 2016/6/29.
 */
public class SDFileConfig {
    private static final String APP_DIR = "VideoDemo";
    public static String SDKCARD_PATH = "";

    /**
     * 斜杠
     */
    public static String SLASH = "/";


    public static String getAppSDKPath() {
        String path = null;
        //TODO
        if (ValueUtil.isStringValid(SDKCARD_PATH)) {
            path = SDKCARD_PATH + SLASH + APP_DIR;
        } else {
            SDKCARD_PATH = Environment
                    .getExternalStorageDirectory().toString();
            path = SDKCARD_PATH + SLASH + APP_DIR;
        }
        return path;
    }

}
