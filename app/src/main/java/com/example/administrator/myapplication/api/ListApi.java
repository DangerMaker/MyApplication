package com.example.administrator.myapplication.api;

import com.example.administrator.myapplication.model.CustemList;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.POST;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-19
 */
public interface ListApi {
    @POST("/EZTableViewData.txt")
    void getNewsList(Callback<String> callback);
}
