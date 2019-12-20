package com.example.homedemo.net

import com.example.homedemo.entity.ImageDataResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 图片的网络接口
 * Created by Tomdog on 2019/12/20.
 */
interface ImageApiService {

    @GET("image/sogou/api.php")
    suspend fun getImage(@Query("type") type :String ="json") : ImageDataResponseBody
}