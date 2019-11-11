package com.example.homedemo.net;

import com.example.homedemo.entity.Translation;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tomdog on 2019/10/29.
 */
public interface TranslationRequest {

    /**
     * 有道翻译-英译汉
     * @return
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getWord();

}
