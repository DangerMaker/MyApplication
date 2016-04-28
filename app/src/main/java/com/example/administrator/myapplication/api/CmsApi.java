package com.example.administrator.myapplication.api;

import com.example.administrator.myapplication.model.Token;

import retrofit.Callback;
import retrofit.http.POST;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface CmsApi {
    @POST("/userapi/user/token")
    void getToken(Callback<Token> callback);
}
