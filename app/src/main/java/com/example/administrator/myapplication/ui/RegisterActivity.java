package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.RegisterSuc;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2016/4/15.
 */
public class RegisterActivity extends BackBaseActivity {
    @Bind(R.id.register_name_edit)
    EditText mNameEdit;
    @Bind(R.id.register_pass_edit)
    EditText mPassEdit;
    @Bind(R.id.register_mail_edit)
    EditText mMailEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setCustomTitle("注册");
    }

    @OnClick(R.id.register_btn)
    public void register() {
        String username = mNameEdit.getText().toString().trim();
        String password = mPassEdit.getText().toString().trim();
        String mail = mMailEdit.getText().toString().trim();
        loadData(username,password,mail);
    }

    private void loadData(String username,String password,String mail) {
        if(TextUtils.isEmpty(username)){
            SystemUtils.show_msg(this,"用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            SystemUtils.show_msg(this,"密码不能为空");
            return;
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mail);
        boolean isMatched = matcher.matches();
        if(TextUtils.isEmpty(mail) || !isMatched){
            SystemUtils.show_msg(this,"请输入正确的邮箱");
            return;
        }
        RestAdapterUtils.getUserApi().register(username, mail, password, new Callback<RegisterSuc>() {
            @Override
            public void success(RegisterSuc registerSuc, Response response) {
                if (registerSuc != null && registerSuc.getUid() != null)
                    SystemUtils.show_msg(RegisterActivity.this, "注册成功");

            }

            @Override
            public void failure(RetrofitError error) {
                SystemUtils.show_msg(RegisterActivity.this, "注册失败" + error.getMessage());
            }
        });
    }
}
