package com.example.homedemo.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

/**
 * Created by Tomdog on 2019/12/20.
 */
object ImageNetworkService {

    //retrofit 实例，在这里统一网络配置，如添加转换器，设置超时时间
    private val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).build())
            .baseUrl("https://api.ooopn.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //网络层访问服务
    val apiService = retrofit.create<ImageApiService>()

}