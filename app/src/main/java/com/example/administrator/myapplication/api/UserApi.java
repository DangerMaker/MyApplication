package com.example.administrator.myapplication.api;

import com.example.administrator.myapplication.model.AlterMailSuc;
import com.example.administrator.myapplication.model.AlterPassSuc;
import com.example.administrator.myapplication.model.LoginSuc;
import com.example.administrator.myapplication.model.RegisterSuc;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface UserApi {
    @FormUrlEncoded
    @POST("/userapi/user/register")
    void register(@Field("name") String name,
                  @Field("mail") String mail,
                  @Field("pass") String pass,
                  Callback<RegisterSuc> callback);

    @FormUrlEncoded
    @POST("/userapi/user/login")
    void login(
            @Field("username") String username,
            @Field("password") String password,
            Callback<LoginSuc> callback);

    @FormUrlEncoded
    @POST("/userapi/user/logout")
    void loginOut(@Header("X-CSRF-TOKEN") String token,
                  @Header("Cookie") String cookie,
                  @Field("uid") String uid,
                  Callback<List<String>> callback);

    //个人信息
    @GET("/userapi/user/{uid}.json")
    void getPersonalData(
            @Header("X-CSRF-TOKEN") String token,
            @Header("Cookie") String cookie,
            @Path("uid") String uid,
            Callback<String> callback);

    //修改
    @FormUrlEncoded
    @PUT("/userapi/user/{uid}")
    void alterPersonData(
            @Header("X-CSRF-TOKEN") String token,
            @Header("Cookie") String cookie,
            @Path("uid") String uid,
            @Field("mail") String mail,
            @Field("current_pass") String pass,
            Callback<String> callback);

    //修改mail
    @FormUrlEncoded
    @PUT("/userapi/user/{uid}")
    void alterMail(
            @Header("X-CSRF-TOKEN") String token,
            @Header("Cookie") String cookie,
            @Path("uid") String uid,
            @Field("mail") String mail,
            @Field("current_pass") String pass,
            Callback<AlterMailSuc> callback);

    //修改pass
    @FormUrlEncoded
    @PUT("/userapi/user/{uid}")
    void alterPass(
            @Header("X-CSRF-TOKEN") String token,
            @Header("Cookie") String cookie,
            @Path("uid") String uid,
            @Field("pass") String password,
            @Field("current_pass") String pass,
            Callback<AlterPassSuc> callback);

    @Multipart
    @POST("/userapi/user/file")
    void upFile(
            @Header("X-CSRF-TOKEN") String token,
            @Header("Cookie") String cookie,
            @Part("filename") String filename,
            @Part("file") TypedFile file,
            Callback<String> callback);

}