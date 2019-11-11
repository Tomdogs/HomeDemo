package com.example.homedemo.net;

import com.example.homedemo.entity.News;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tomdog on 2019/10/28.
 */
public interface NewsRequest {

    //网易-健康新闻
    @GET("nc/article/list/T1414389941036/0-20.html")
    Call<News> getHealthNews();


}
