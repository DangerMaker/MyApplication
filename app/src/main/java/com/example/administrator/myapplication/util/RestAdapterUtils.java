package com.example.administrator.myapplication.util;

import android.content.Context;
import android.util.Log;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.api.FriendApi;
import com.example.administrator.myapplication.api.ListApi;
import com.example.administrator.myapplication.api.UserApi;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by lyjq 该类为retrofit的辅助类，封装okHttpClient发出的网络请求
 */
public class RestAdapterUtils {

    /**
     * support offline cache
     * @param endpoint 域地址
     * @param service 请求接口api
     * @param ctx 上下文
     * @param <T>
     * @return
     */
    public static <T> T getRestAPI(String endpoint, Class<T> service, final Context ctx) {

        RestAdapter.LogLevel level;
        //log的等级 full则打印全部网络信息
        level = RestAdapter.LogLevel.FULL;
        //设置OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        try{
            int cacheSize = 10 * 1024 * 1024;
            //设置磁盘缓存
            Cache cache = new Cache(ctx.getCacheDir(), cacheSize);
            okHttpClient.setCache(cache);
        }catch (Exception ex){
            Log.e("RestAdapterUtils", "cache error");
        }
        //通过建造者模式，选择性进行网络参数的配置
        RestAdapter restAdapter = new RestAdapter.Builder()
                //日志输出量
                .setLogLevel(level)
                //根地址
                .setEndpoint(endpoint)
                //设置client
                .setClient(new OkClient(okHttpClient))
                //设置拦截器
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //判断网络状态
                        if (NetworkUtils.isNetworkAvaliable(ctx)) {
                            int maxAge = 600; // read from cache for 10 minute
                            request.addHeader("Cache-Control", "public, max-age=" + maxAge);
                        } else {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                            request.addHeader("Cache-Control",
                                    "public, only-if-cached, max-stale=" + maxStale);
                        }
                    }
                })
                .build();

        return restAdapter.create(service);
    }

    /**
     * nonsupport offline cache 重载该方法 可以通过参数设置是否加磁盘缓存
     * @param endpoint 域地址
     * @param service 请求接口api
     * @param <T>
     * @return
     */
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

    /**
     * gson无法自动进行pojo则以string类型返回
     * @param endpoint 域地址
     * @param service 请求接口api
     * @param <T>
     * @return
     */
    public static <T> T getRestAPIForString(String endpoint, Class<T> service) {

        RestAdapter.LogLevel level;
        level = RestAdapter.LogLevel.FULL;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(level)
                .setEndpoint(endpoint)
                .setClient(new OkClient())
                //替换默认GsonConverter,使返回数据为字符串
                .setConverter(new StringConverter())
                .build();

        return restAdapter.create(service);
    }

    /**
     * 用户相关接口
     * @return
     */
    public static UserApi getUserApi(){
        return RestAdapterUtils.getRestAPI(Config.USER_URL,UserApi.class);
    }

    /**
     * 通用列表接口
     * @return
     */

    public static ListApi getListApi(){
        return RestAdapterUtils.getRestAPIForString(Config.CMS_NEWSLIST_URL,ListApi.class);
    }

    /**
     * 圈子接口
     * @return
     */
    public static FriendApi getFriendApi(){
        return RestAdapterUtils.getRestAPI(Config.FRIEND_URL,FriendApi.class);
    }

}
