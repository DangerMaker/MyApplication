package com.example.administrator.myapplication.util;

import android.content.Context;
import android.util.Log;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.api.CmsApi;
import com.example.administrator.myapplication.api.FriendApi;
import com.example.administrator.myapplication.api.ListApi;
import com.example.administrator.myapplication.api.UserApi;
import com.example.administrator.myapplication.dao.User;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by lyjq
 */
public class RestAdapterUtils {

    /**
     * support offline cache
     * @param endpoint
     * @param service
     * @param ctx
     * @param <T>
     * @return
     */
    public static <T> T getRestAPI(String endpoint, Class<T> service, final Context ctx) {

        RestAdapter.LogLevel level;
        level = RestAdapter.LogLevel.FULL;

        OkHttpClient okHttpClient = new OkHttpClient();
        try{
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(ctx.getCacheDir(), cacheSize);
            okHttpClient.setCache(cache);
        }catch (Exception ex){
            Log.e("RestAdapterUtils", "cache error");
        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(level)
                .setEndpoint(endpoint)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        if (NetworkUtils.isNetworkAvaliable(ctx)) {
                            Log.e("cache", "10min");
                            int maxAge = 600; // read from cache for 10 minute
                            request.addHeader("Cache-Control", "public, max-age=" + maxAge);
                        } else {
                            Log.e("cache", "4weaks");
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                            request.addHeader("Cache-Control",
                                    "public, only-if-cached, max-stale=" + maxStale);
                        }
                    }
                })
                .build();

        return restAdapter.create(service);
    }

    public static <T> T getRestAPI(String endpoint, Class<T> service) {

        RestAdapter.LogLevel level;
        level = RestAdapter.LogLevel.FULL;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(level)
                .setEndpoint(endpoint)
                .setClient(new OkClient())
                .build();

        return restAdapter.create(service);
    }

    public static <T> T getRestAPIForString(String endpoint, Class<T> service) {

        RestAdapter.LogLevel level;
        level = RestAdapter.LogLevel.FULL;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(level)
                .setEndpoint(endpoint)
                .setClient(new OkClient())
                .setConverter(new StringConverter())
                .build();

        return restAdapter.create(service);
    }

    public static UserApi getUserStringApi(){
        return RestAdapterUtils.getRestAPIForString(Config.USER_URL,UserApi.class);
    }

    public static UserApi getUserApi(){
        return RestAdapterUtils.getRestAPI(Config.USER_URL,UserApi.class);
    }

    public static ListApi getListApi(){
        return RestAdapterUtils.getRestAPIForString(Config.CMS_NEWSLIST_URL,ListApi.class);
    }

    public static FriendApi getFriendApi(){
        return RestAdapterUtils.getRestAPI(Config.FRIEND_URL,FriendApi.class);
    }

}
