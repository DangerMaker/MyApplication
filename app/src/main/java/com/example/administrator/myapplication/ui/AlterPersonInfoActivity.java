package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.BackBaseActivity;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2016/4/15.
 */
public class AlterPersonInfoActivity extends BackBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        setCustomTitle("修改个人信息");
//        loadData();
    }

//    public void loadData(){
//        RestAdapterUtils.getUserStringApi().alterPersonData(Config.token, Config.cookie, Config.uid, "1",
//                "123@li.com", Config.password, new Callback<String>() {
//                    @Override
//                    public void success(String s, Response response) {
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                    }
//                });
//
//    }

}
