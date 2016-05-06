package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.auth.UserService;
import com.example.administrator.myapplication.model.LoginSuc;
import com.example.administrator.myapplication.model.User;
import com.example.administrator.myapplication.model.map.User2UserData;
import com.example.administrator.myapplication.util.EditTextUtils;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2016/4/15.
 */
public class LoginActivity extends BackBaseActivity {

    @Bind(R.id.login_name_edit)
    EditText mNameEdit;
    @Bind(R.id.login_pass_edit)
    EditText mPassEdit;
    @Bind(R.id.login_name_image)
    ImageView mNameImage;
    @Bind(R.id.login_pass_image)
    ImageView mPassImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setCustomTitle("登录");
        initView();

    }

    private void initView() {
        EditTextUtils.setEditTextImage(mNameEdit,mNameImage);
        EditTextUtils.setEditTextImage(mPassEdit,mPassImage);
    }


    @OnClick(R.id.login_btn)
    public void login() {
        String username = mNameEdit.getText().toString().trim();
        String password = mPassEdit.getText().toString().trim();
        loginload(username,password);
    }

    @OnClick(R.id.login_to_register)
    public void register(){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    private void loginload(String username,String password) {
        if(TextUtils.isEmpty(username)){
            SystemUtils.show_msg(this,"用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            SystemUtils.show_msg(this,"密码不能为空");
            return;
        }

        RestAdapterUtils.getUserApi().login(username, password, new Callback<LoginSuc>() {
            @Override
            public void success(LoginSuc loginSuc, Response response) {
                if (loginSuc != null) {
                    SystemUtils.show_msg(LoginActivity.this, "登录成功");
                    Config.token = loginSuc.getToken();
                    finishLogin(loginSuc.getUser(),response);
                }
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
                SystemUtils.show_msg(LoginActivity.this, "登录失败" + error.getMessage());
            }
        });
    }

    private void finishLogin(User user,Response response){

        if(mNameEdit == null || mPassEdit == null){
            return;
        }

        UserService.getInstance(this).signIn(mNameEdit.getText().toString(), mPassEdit.getText().toString(), User2UserData.transfrom(user));
        String headers = null;
        for (int i = 0; i < response.getHeaders().size(); i++) {
            if (response.getHeaders().get(i).getName().equals("Set-Cookie")) {
                headers = response.getHeaders().get(i).getValue();
                String temp[] = headers.split(";");
                headers = temp[0];
                Log.e("set-cookie", headers);
            }
        }

        Config.uid = user.getUid();
        Config.cookie = headers;
        Config.username = user.getName();
        finish();
    }
}
