package com.example.administrator.myapplication.api;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-26
 */

import com.example.administrator.myapplication.model.FriendGroupListModel;

import retrofit.Callback;
import retrofit.http.GET;

public interface  FriendApi  {
    @GET("/circle_json.txt")
    void getFriendGroupList(Callback<FriendGroupListModel> callback);
}
