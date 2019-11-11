package com.example.homedemo.utils;

/**
 * Created by Tomdog on 2018/8/14.
 */

public class Config {
    //是否调试  //TODO 在发布的时候false
    public static boolean DEBUG = true;

    //Search
    public static String SEARCH_CITY = "北京";
    public static String SEARCH_CITY_CODE = "110000";

    public static final String checked_update= "checked_update";//检查过的app版本

    /**
     * 以下为新增
     */
    public static final String look_type = "look_type";//用户上次打开选中的图层;//卫星-160，2D-120 ，3D-140
    public static int ImageCount = 5;//信息采集最大拍照，相册数


    public static final int CHOOSE_CAMERA = 0;//请求码，拍照
    public static final int CHOOSE_ALBUM = 1;//请求码，相册
    public static final int CHOOSE_VIDEO = 2;//请求码，视频
    public static final int CHOOSE_POINT = 3;//请求码，地图选点
    public static final int CHOOSE_LINE = 4;//请求码，地图选线
    public static final int CHOOSE_AREA = 5;//请求码，地图选面
    public static final int ERROR_CAMERA = 11111;//返回码，没有相机错误
}
